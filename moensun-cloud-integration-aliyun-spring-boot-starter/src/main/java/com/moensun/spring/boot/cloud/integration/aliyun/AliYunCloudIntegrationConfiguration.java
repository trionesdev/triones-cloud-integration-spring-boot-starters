package com.moensun.spring.boot.cloud.integration.aliyun;

import com.moensun.spring.boot.cloud.integration.aliyun.oss.AliYunOssAutoConfiguration;
import com.moensun.spring.boot.cloud.integration.aliyun.sms.AliYunSmsAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value = "com.moensun.spring.boot.cloud.integration.aliyun.AliYunCloudIntegrationConfiguration")
@Import(value = {
        AliYunOssAutoConfiguration.class,
        AliYunSmsAutoConfiguration.class
})
public class AliYunCloudIntegrationConfiguration {
}
