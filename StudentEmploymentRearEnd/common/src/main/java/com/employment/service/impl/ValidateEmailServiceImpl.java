package com.employment.service.impl;

import com.employment.constant.RedisConstant;
import com.employment.enums.AppHttpCodeEnum;
import com.employment.exception.AppSystemException;
import com.employment.service.ValidateEmailService;
import com.employment.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ValidateEmailServiceImpl
 * @author: c9noo
 * @create: 2023-12-13 13:10
 * @Version 1.0
 **/
@Service
public class ValidateEmailServiceImpl implements ValidateEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 进行校验
     * @param email
     * @return
     */
    @Override
    public boolean sendValidateLimitation(String email) {

        String text = redisUtil.getCacheObject(RedisConstant.REDIS_EMAIL + email);
        if (!Objects.isNull(text)){
            throw new AppSystemException(AppHttpCodeEnum.EMAIL_TIME_NOT_OUT);
        }

        return true;
    }


    @Override
    public void sendPasswordResetEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
