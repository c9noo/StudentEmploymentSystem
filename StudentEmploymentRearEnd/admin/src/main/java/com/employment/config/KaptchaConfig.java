package com.employment.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

import java.util.Properties;

/**
 * @program: StudentEmploymentSystem
 * @ClassName KaptchaConfig
 * @author: c9noo
 * @create: 2023-12-12 16:43
 * @Version 1.0
 * 谷歌验证码生成器配置文件
 **/
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {

        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "4");
        properties.put("kaptcha.image.height", "40");
        properties.put("kaptcha.image.width", "120");
        properties.put("kaptcha.textproducer.font.size", "30");

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
