package com.employment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: StudentEmploymentSystem
 * @ClassName JwtProperties
 * @author: c9noo
 * @create: 2023-11-13 21:25
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "student.jwt")
@Data
public class JwtProperties {
    private Long time;
    private String key;
    private String word;
    private String jwtName;
}
