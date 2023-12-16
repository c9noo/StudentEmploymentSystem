package com.employment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: StudentEmploymentSystem
 * @ClassName AliOssProperties
 * @author: c9noo
 * @create: 2023-11-13 19:08
 * @Version 1.0
 * 阿里云oss的配置文件
 **/
@Component
@ConfigurationProperties(prefix = "student.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
