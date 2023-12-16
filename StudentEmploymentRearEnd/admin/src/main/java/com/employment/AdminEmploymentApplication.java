package com.employment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启事务处理注解
public class AdminEmploymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminEmploymentApplication.class,args);
    }
}