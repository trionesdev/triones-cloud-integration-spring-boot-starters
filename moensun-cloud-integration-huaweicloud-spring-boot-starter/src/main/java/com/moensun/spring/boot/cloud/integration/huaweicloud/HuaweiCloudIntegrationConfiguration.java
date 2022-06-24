package com.moensun.spring.boot.cloud.integration.huaweicloud;

import com.moensun.spring.boot.cloud.integration.huaweicloud.obs.HuaweiCloudObsAutoConfiguration;
import com.moensun.spring.boot.cloud.integration.huaweicloud.sms.HuaweiCloudSmAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value = "com.moensun.spring.boot.cloud.integration.huaweicloud.HuaweiCloudIntegrationConfiguration")
@Import(value = {
        HuaweiCloudObsAutoConfiguration.class,
        HuaweiCloudSmAutoConfiguration.class
})
public class HuaweiCloudIntegrationConfiguration {
}
