package com.radkevich.couriersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableWebSecurity
@EnableRetry
@EnableTransactionManagement
public class CouriersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouriersServiceApplication.class, args);
    }

}
