package com.employment.config;

import com.employment.properties.JwtProperties;
import com.employment.utils.JwtUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: StudentEmploymentSystem
 * @ClassName JwtConfig
 * @author: c9noo
 * @create: 2023-11-13 21:27
 * @Version 1.0
 * Jwt配置类
 **/
@Configuration
public class JwtConfig {

    /**
     * 创建Jwt工具类对象，交给Spring容器管理
     * @param jwtProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtUtil jwtUtil(JwtProperties jwtProperties){
        return new JwtUtil(
                jwtProperties.getTime(),
                jwtProperties.getKey(),
                jwtProperties.getWord(),
                jwtProperties.getJwtName()
        );
    }

}
