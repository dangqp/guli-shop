package com.dangqp.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient  //nacos注册
@ComponentScan(basePackages = {"com.dangqp"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run( EduApplication.class, args );
    }
}
