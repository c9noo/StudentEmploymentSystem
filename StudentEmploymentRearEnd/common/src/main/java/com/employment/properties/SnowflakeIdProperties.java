package com.employment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: StudentEmploymentSystem
 * @ClassName SnowflakeIdProperties
 * @author: c9noo
 * @create: 2023-11-26 20:33
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "student.snowflake")
@Data
public class SnowflakeIdProperties {
    private long workerId;
    private long dataCenterId;
}
