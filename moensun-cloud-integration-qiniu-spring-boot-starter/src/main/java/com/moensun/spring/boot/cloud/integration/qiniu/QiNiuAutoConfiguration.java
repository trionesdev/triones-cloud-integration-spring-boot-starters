package com.moensun.spring.boot.cloud.integration.qiniu;

import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "qiniu",value = {"enabled"},havingValue = "true")
@EnableConfigurationProperties(value = {QiNiuConfProperties.class})
public class QiNiuAutoConfiguration {

    private final QiNiuConfProperties qiNiuConfProperties;

    @Bean
    public Auth auth(){
        return Auth.create(qiNiuConfProperties.getAccessKey(), qiNiuConfProperties.getSecretKey());
    }
}
