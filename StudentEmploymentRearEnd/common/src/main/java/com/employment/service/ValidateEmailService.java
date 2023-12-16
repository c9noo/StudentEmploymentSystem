package com.employment.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * @program: StudentEmploymentSystem
 * @ClassName ValidateEmailService
 * @author: c9noo
 * @create: 2023-12-13 13:09
 * @Version 1.0
 **/
public interface ValidateEmailService {

    /**
     * 进行校验
     * @param email
     * @return
     */
    boolean sendValidateLimitation(String email);

    /**
     * 发送邮箱
     * @param passwordResetEmail
     */
    void sendPasswordResetEmail(SimpleMailMessage passwordResetEmail);
}
