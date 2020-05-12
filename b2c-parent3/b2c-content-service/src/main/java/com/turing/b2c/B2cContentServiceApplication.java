package com.turing.b2c;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableDubbo
@EnableCaching
@SpringBootApplication
public class B2cContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cContentServiceApplication.class, args);
    }

}
