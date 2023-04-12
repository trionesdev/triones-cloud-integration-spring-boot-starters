package com.moensun.spring.boot.cloud.integration.other.minio;

import com.moensun.spring.boot.cloud.integration.other.minio.annotations.EnableMinioClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMinioClients
public class MinioApp {
    public static void main(String[] args) {
        SpringApplication.run(MinioApp.class, args);
    }
}
