package com.employment.config;

import com.employment.properties.SnowflakeIdProperties;
import com.employment.utils.SnowflakeIdUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: StudentEmploymentSystem
 * @ClassName SnowflakeConfig
 * @author: c9noo
 * @create: 2023-11-26 21:03
 * @Version 1.0
 **/
@Configuration
public class SnowflakeConfig {

    /**
     * 创建snowflakeIdUtils对象，交给Spring容器管理
     * @param snowflakeIdProperties
     * @return
     */

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIdUtils snowflakeIdUtils(SnowflakeIdProperties snowflakeIdProperties){
        return new SnowflakeIdUtils(snowflakeIdProperties.getWorkerId(),snowflakeIdProperties.getDataCenterId());
    }

}
