package com.dangqp.service.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.dangqp"})//扫描 可以使用swagger-ui
public class ServiceOssApplication {

    public static void main(String[] args) {
        SpringApplication.run( ServiceOssApplication.class, args );
    }

}
