package com.innovation.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 高校创新创业项目孵化平台启动类
 */
@SpringBootApplication
@MapperScan("com.innovation.platform.mapper")
public class InnovationPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(InnovationPlatformApplication.class, args);
    }
}
