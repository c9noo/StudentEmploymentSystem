package com.employment.service.impl;

import com.employment.constant.RedisConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.mapper.MenuMapper;
import com.employment.mapper.UserMapper;
import com.employment.mapper.UserRoleMapper;
import com.employment.pojo.dto.LoginUserDto;
import com.employment.pojo.dto.ResetPasswordUser;
import com.employment.pojo.dto.UpdateUserDto;
import com.employment.pojo.dto.UpdateUserPasswordDto;
import com.employment.pojo.entity.Menu;
import com.employment.pojo.entity.User;
import com.employment.pojo.vo.*;
import com.employment.result.ResponseResult;
import com.employment.service.UserService;
import com.employment.service.ValidateEmailService;
import com.employment.utils.BeanCopyUtil;
import com.employment.utils.JwtUtil;
import com.employment.utils.RedisUtil;
import com.employment.utils.SecurityUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.employment.constant.RedisConstant.*;

/**
 * @program: StudentEmploymentSystem
 * @ClassName UserServiceImpl
 * @author: c9noo
 * @create: 2023-11-06 14:41
 * @Version 1.0
 * 用户功能的业务处理
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DefaultKaptcha producer;

    @Autowired
    private ValidateEmailService validateEmailService;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 用户登录功能
     * @param loginUserDto
     * @return
     */
    @Override
    public ResponseResult login(LoginUserDto loginUserDto) {

        validate(loginUserDto);

        // 封装成UsernamePasswordAuthenticationToken对象
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginUserDto.getUsername(), loginUserDto.getPassword()
        );

        // 调用authenticate 进行认证
        Authentication authentication = authenticationManager.authenticate(authRequest);

        // 判断认证结果，如果为空代表认证失败
        Optional.ofNullable(authentication)
                .orElseThrow(() -> new AppSystemException(AppHttpCodeEnum.LOGIN_ERROR));

        // 存入redis中，key为 system:id 值
        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        String id = loginUserVo.getUser().getId().toString();
        String redisKey = RedisConstant.REDIS_USER_KEY + id;
        redisUtil.setCacheObject(redisKey, loginUserVo);
        redisUtil.expire(redisKey,REDIS_USER_TIME);

        // 将token返回给前端
        String jwt = jwtUtil.createJWT(id);
        Map<String, String> tokenMap = Collections.singletonMap(jwtUtil.getJwtName(), jwt);

        return ResponseResult.okResult(tokenMap);
    }

    /**
     * 校验验证码
     * @param loginUserDto
     */
    private void validate(LoginUserDto loginUserDto) {

        String captcha = loginUserDto.getCaptcha();

        String uuid = loginUserDto.getUuid();

        String captchaKey = REDIS_CAPTCHA + uuid;

        if (!captcha.equals(redisUtil.getCacheObject(captchaKey))){
            throw new AppSystemException(AppHttpCodeEnum.CAPTCHA_ERROR);
        }
        redisUtil.deleteObject(captchaKey);
    }


    /**
     * 获取用户信息、角色信息、权限信息
     */
    @Override
    public UserInfoVo getInfo() {
        //获取到用户的信息拷贝成UserVo对象
        User user = SecurityUtil.getLoginUserVo().getUser();
        UserVo userVo = BeanCopyUtil.copyBean(user, UserVo.class);
        //根据用户id获取到对应的角色信息
        List<String> roleKeys = userRoleMapper.getRoleKeyByUserId(SecurityUtil.getUserId());
        //获取到用户对应的权限信息
        List<String> permissions = SecurityUtil.getLoginUserVo().getPermissions();

        return new UserInfoVo(userVo, roleKeys, permissions);
    }

    /**
     * 查询用户对应的菜单
     */
    @Override
    public MenusVo getMenus() {
        List<Menu> menus = menuMapper.getMenusByUserId(SecurityUtil.getUserId());
        List<MenuVo> menuVos = BeanCopyUtil.copyBeanList(menus, MenuVo.class);
        List<MenuVo> menuTree = buildMenuTree(menuVos, 0L);

        return new MenusVo(menuTree);
    }

    /**
     * 用户退出功能
     */
    @Override
    public ResponseResult logout() {
        redisUtil.deleteObject(RedisConstant.REDIS_USER_KEY + SecurityUtil.getUserId());
        return ResponseResult.okResult();
    }

    /**
     * 根据Token修改用户信息
     */
    @Override
    public ResponseResult updateUserByToken(UpdateUserDto updateUserDto) {

        //获取用户id
        Long userId = SecurityUtil.getUserId();

        //将要修改的信息拷贝为User对象，并且将用户的id传值进去
        User user = BeanCopyUtil.copyBean(updateUserDto, User.class);
        user.setId(userId);
        userMapper.updateUserById(user);

        //取出最新的用户数据
        LoginUserVo loginUserVo = SecurityUtil.getLoginUserVo();
        Optional<User> userByUsername = userMapper.getUserByUsername(loginUserVo.getUsername());
        loginUserVo.setUser(userByUsername.get());

        // 添加到SecurityContextHolder中
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(loginUserVo, null, null)
        );

        //更新redis的缓存
        String redisKey = RedisConstant.REDIS_USER_KEY + userId;
        redisUtil.setCacheObject(redisKey, loginUserVo);
        redisUtil.expire(redisKey,REDIS_USER_TIME);

        return ResponseResult.okResult();
    }

    /**
     * 获取验证码
     * @return
     * @throws IOException
     */
    @Override
    public ResponseResult getCaptcha() throws IOException {
        //     生成uuid、生成验证码，返回uuid和验证码图片给前端
        String uuid = UUID.randomUUID().toString();
        String captcha = producer.createText();


        BufferedImage image = producer.createImage(captcha);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());

        // 存储到redis中，所以redis中的map数据结构存储
        String captchaKey = REDIS_CAPTCHA + uuid;
        redisUtil.setCacheObject(captchaKey,captcha);
        redisUtil.expire(captchaKey,REDIS_CAPTCHA_TIME);

        HashMap<String, String> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("base64Image",base64Img);
        log.info(captcha);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult updateUserPassword(UpdateUserPasswordDto updateUserPassword) {

        //将oldPassword进行加密 比较
        if (!passwordEncoder.matches(updateUserPassword.getOldPassword(), SecurityUtil.getLoginUserVo().getPassword())){
            throw new AppSystemException(AppHttpCodeEnum.OLD_PASSWORD_ERROR);
        }

        //获取用户的id
        Long userId = SecurityUtil.getUserId();

        //创建user对象
        User user = User.builder()
                .id(userId)
                .password(passwordEncoder.encode(updateUserPassword.getNewPassword()))
                .build();

        //修改
        userMapper.updateUserById(user);

        return logout();
    }

    /**
     * 发送邮箱
     * @param email
     * @return
     */
    @Override
    public ResponseResult sendValidationEmail(String email, HttpServletRequest request) {

        //根据邮箱查询用户
        User user = userMapper.getUserByEmailOrPhone(email,null);
        if (Objects.isNull(user)){
            throw new AppSystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //进行校验
        if (validateEmailService.sendValidateLimitation(email)){

            //设置验证码
            String text = producer.createText();

            //设置redis的key
            String key = REDIS_EMAIL+email;

            //设置邮件内容
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom(from);
            passwordResetEmail.setTo(email);
            passwordResetEmail.setSubject("【学生就业管理系统】忘记密码");
            passwordResetEmail.setText("您正在申请重置密码，验证码为:"+text+",有效期为三分钟");
            redisUtil.setCacheObject(key, text);
            redisUtil.expire(key, REDIS_EMAIL_TIME);
            validateEmailService.sendPasswordResetEmail(passwordResetEmail);

        }
        return ResponseResult.okResult(email);
    }

    /**
     * 重置密码
     * @param resetPasswordUser
     * @return
     */
    @Override
    public ResponseResult reset(ResetPasswordUser resetPasswordUser) {

        String text = redisUtil.getCacheObject(REDIS_EMAIL + resetPasswordUser.getEmail());
        if (!text.equals(resetPasswordUser.getText())) {
            throw new AppSystemException(AppHttpCodeEnum.CAPTCHA_ERROR);
        }
        //校验成功 修改密码
        User build = User.builder()
                .password(passwordEncoder.encode(resetPasswordUser.getPassword()))
                .email(resetPasswordUser.getEmail()).build();

        userMapper.updatePasswordByEmail(build);

        //删除redis中的数据
        redisUtil.deleteObject(REDIS_EMAIL + resetPasswordUser.getEmail());

        return ResponseResult.okResult();
    }

    /**
     * 将集合转换为Tree的形式
     */
    private List<MenuVo> buildMenuTree(List<MenuVo> menuVos, Long parentId) {
        return menuVos.stream()
                .filter(menuVo -> parentId.equals(menuVo.getParentId()))
                .map(menuVo -> menuVo.setChildren(buildMenuTree(menuVos, menuVo.getId())))
                .collect(Collectors.toList());
    }


}
