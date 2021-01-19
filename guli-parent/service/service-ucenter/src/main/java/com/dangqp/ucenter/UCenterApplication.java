package com.dangqp.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Title:com.dangqp.ucenter
 * Description:
 * Copyright: Copyright (c) 2021
 *
 * @author dangqp
 * @version 1.0
 * @created 2021/01/16  19:46
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.dangqp"})
@MapperScan("com.dangqp.*.mapper")
public class UCenterApplication {
    
    public static void main(String[] args){
        SpringApplication.run( UCenterApplication.class,args );
    }
    
}
