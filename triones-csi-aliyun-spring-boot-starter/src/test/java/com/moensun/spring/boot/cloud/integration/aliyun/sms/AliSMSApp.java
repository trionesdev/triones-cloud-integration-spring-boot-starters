package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.moensun.csi.aliyun.sms.annotation.EnableAliYunSMSClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAliYunSMSClients
public class AliSMSApp {
    public static void main(String[] args) {
        SpringApplication.run(AliSMSApp.class, args);
    }
}