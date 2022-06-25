package com.moensun.spring.boot.cloud.integration.tencentcloud;

import com.moensun.spring.boot.cloud.integration.tencentcloud.cos.TencentCloudCosAutoConfiguration;
import com.moensun.spring.boot.cloud.integration.tencentcloud.ocr.TencentCloudOcrAutoConfiguration;
import com.moensun.spring.boot.cloud.integration.tencentcloud.sms.TencentCloudSmsAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value = "com.moensun.spring.boot.cloud.integration.tencentcloud.TencentCloudIntegrationConfiguration")
@Import(value = {
        TencentCloudCosAutoConfiguration.class,
        TencentCloudSmsAutoConfiguration.class,
        TencentCloudOcrAutoConfiguration.class
})
public class TencentCloudIntegrationConfiguration {
}
