package com.turing.b2c;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class B2cSearchServiceApplication {

    public static void main(String[] args) {
        //和netty的一个端口号冲突，禁用
        System.setProperty("es.set.netty.runtime.available.processors", "false");//手动加上
        SpringApplication.run(B2cSearchServiceApplication.class, args);
    }

}
