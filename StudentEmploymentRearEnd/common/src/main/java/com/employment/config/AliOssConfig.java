package com.employment.config;

import com.employment.properties.AliOssProperties;
import com.employment.utils.AliOssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AliOssConfig
 * @author: c9noo
 * @create: 2023-11-13 19:31
 * @Version 1.0
 * 阿里云oss配置文件
 **/
@Configuration
public class AliOssConfig {

    /**
     * 进行创建一个阿里云oss工具类对象，并且交给Spring管理
     * @param aliOssProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }

}
