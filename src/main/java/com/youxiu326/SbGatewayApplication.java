package com.youxiu326;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.youxiu326.gateway.dao")
public class SbGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbGatewayApplication.class, args);
    }

}
