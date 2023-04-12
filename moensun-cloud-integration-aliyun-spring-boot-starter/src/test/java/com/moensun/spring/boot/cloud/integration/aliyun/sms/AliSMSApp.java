package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations.EnableSMSClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSMSClients
public class AliSMSApp {
    public static void main(String[] args) {
        SpringApplication.run(AliSMSApp.class, args);
    }
}
