package com.moensun.spring.boot.cloud.integration.tencentcloud.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "tencentcloud.sms")
public class TencentCloudSmsConfProperties extends TencentCloudSmsProperties{
    private static final long serialVersionUID = 6472644374509717708L;
    private Boolean enabled;
    private Map<String, TencentCloudSmsInstanceProperties> instance;
}
