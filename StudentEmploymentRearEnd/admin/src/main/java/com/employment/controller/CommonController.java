package com.employment.controller;

import com.employment.constant.AuthorityConstant;
import com.employment.constant.FileSuffixConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.pojo.dto.ImportAlumniDto;
import com.employment.pojo.dto.ImportUserDto;
import com.employment.pojo.dto.ResetPasswordUser;
import com.employment.pojo.entity.User;
import com.employment.result.ResponseResult;
import com.employment.service.UserService;
import com.employment.service.ValidateEmailService;
import com.employment.service.impl.ExcelServiceImpl;
import com.employment.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @program: StudentEmploymentSystem
 * @ClassName CommonController
 * @author: c9noo
 * @create: 2023-11-14 08:35
 * @Version 1.0
 * 通用接口相关的Controller
 **/
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用相关接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private ExcelServiceImpl readExcel;

    @Autowired
    private UserService userService;


    /**
     * 发送忘记密码邮箱请求，每次间隔不低于三分钟
     * @param email
     * @param request
     * @return
     */
    @GetMapping("/email")
    @ApiOperation("找回密码")
    public ResponseResult sendValidationEmail(@RequestParam("email") String email,HttpServletRequest request){
        return userService.sendValidationEmail(email,request);
    }

    /**
     * 重置密码
     * @param resetPasswordUser
     * @return
     */
    @PutMapping("/reset")
    @ApiOperation("重置密码")
    public ResponseResult reset(@RequestBody @Validated ResetPasswordUser resetPasswordUser){
        return userService.reset(resetPasswordUser);
    }


    @PostMapping("/upload")
    @ApiOperation("文件上传功能")
    public ResponseResult upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀，忽略大小写
            String fileSuffix = Optional.ofNullable(originalFilename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf('.') + 1))
                    .orElse("");

            // 判断后缀是否合格
            if (!isValidFileExtension(fileSuffix)) {
                return ResponseResult.errorResult(AppHttpCodeEnum.FILE_EXTENSION_NOT_ALLOWED);
            }

            // 重新拼接文件名
            String upload = aliOssUtil.upload(file.getBytes(), UUID.randomUUID().toString() + "." + fileSuffix);
            return ResponseResult.okResult(upload);
        } catch (IOException e) {
            log.error("文件上传失败", e);
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.FILE_UPLOAD);
    }

    /**
     * 下载批量插入校友的模板
     * @param response
     */
    @GetMapping("/exportAlumni")
    @ApiOperation("下载批量插入校友的模板")
    public void downloadExcelAlumni(HttpServletResponse response) {
        downloadExcelTemplate("批量插入校友模板.xlsx", "template/批量插入校友模板.xlsx", response);
    }

    /**
     * 下载批量插入用户的模板
     * @param response
     */
    @GetMapping("/export")
    @ApiOperation("下载批量插入用户的模板")
    public void downloadExcel(HttpServletResponse response) {
        downloadExcelTemplate("批量注册模板.xlsx", "template/批量注册模板.xlsx", response);
    }

    /**
     * 下载Excel模板的通用方法
     *
     * @param filename     文件名
     * @param templatePath 模板路径
     * @param response     HttpServletResponse
     */
    private void downloadExcelTemplate(String filename, String templatePath, HttpServletResponse response) {
        try {
            // 加载 resources/template 目录下的 Excel 模板文件
            Resource resource = new ClassPathResource(templatePath);
            InputStream inputStream = resource.getInputStream();

            // 设置响应头
            String encodedFilename = URLEncoder.encode(filename, "UTF-8");
            encodedFilename = new String(encodedFilename.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFilename + ";charset=UTF-8");
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");

            // 将文件内容写入响应体
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 关闭流
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("文件下载失败: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 从excel中读取用户数据
     * @param file
     * @return
     * @throws IOException
     */
    @PreAuthorize("@Permission.hasAnyPermission({'" + AuthorityConstant.COMPANY_IMPORT + "', '" + AuthorityConstant.TEACHER_IMPORT + "'})")
    @PostMapping("/importExcel")
    @ApiOperation("读取用户excel")
    public List<ImportUserDto> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        // 获取输入流
        try (InputStream inputStream = file.getInputStream()) {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();

            // 获取文件后缀，忽略大小写
            String fileSuffix = Optional.ofNullable(originalFilename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf('.') + 1))
                    .orElse("");

            // 校验
            isValidFileExtension(fileSuffix);

            // 重新拼接文件名
            String newFilename = UUID.randomUUID().toString() + "." + fileSuffix;

            // 获取当前运行的目录
            String currentDir = System.getProperty("user.dir");

            // 拼接文件保存的绝对路径
            String uploadDir = Paths.get(currentDir, "static", "upload").toString();
            // 创建目录
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                boolean created = uploadDirFile.mkdirs();
                if (!created) {
                    throw new AppSystemException(AppHttpCodeEnum.SYSTEM_ERROR);
                }
            }
            Path filePath = Paths.get(uploadDir, newFilename);

            // 保存到本地
            try (OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // 读取返回
            return readExcel.readExcel(filePath.toString());
        }
    }


    /**
     * 读取校友信息
     * @param file
     * @return
     * @throws IOException
     */
    @PreAuthorize("@Permission.hasPermission('"+AuthorityConstant.ALUMNI_IMPORT+"')")
    @PostMapping("/importAlumniExcel")
    @ApiOperation("读取校友excel")
    public List<ImportAlumniDto> importAlumniExcel(@RequestParam("file") MultipartFile file) throws IOException {
        // 获取资源的相对路径
        Resource resource = new ClassPathResource("/");
        String uploadDir = resource.getFile().getAbsolutePath();
        //获取到文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀，忽略大小写
        String fileSuffix = Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf('.') + 1))
                .orElse("");
        //校验
        isValidFileExtension(fileSuffix);
        // 重新拼接文件名
        String newFilename =UUID.randomUUID().toString() + "." + fileSuffix;
        Path filePath = Paths.get(uploadDir, newFilename);
        //保存到本地
        file.transferTo(filePath.toFile());
        //读取 返回
        return readExcel.readExcelAlumni(filePath.toString());
    }


    /**
     * 校验文件后缀是否满足要求
     *
     * @param fileSuffix 文件后缀
     * @return 相同返回true
     */
    private boolean isValidFileExtension(String fileSuffix) {
        // 设置允许的后缀，对字符进行转义
        String[] allowedFileSuffixes = FileSuffixConstant.IMG_SUFFiX.split("\\|");

        // 使用流来检查是否有与给定文件后缀相匹配的元素
        return Arrays.stream(allowedFileSuffixes)
                .anyMatch(suffix -> suffix.equalsIgnoreCase(fileSuffix));
    }



}
