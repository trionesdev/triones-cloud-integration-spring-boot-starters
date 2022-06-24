package com.moensun.spring.boot.cloud.integration.huaweicloud.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "huaweicloud.sms")
public class HuaweiCloudSmsConfProperties extends HuaweiCloudSmsProperties{
    private Boolean enabled;
    private Map<String,HuaweiCloudSmsInstanceProperties> instance;
}
