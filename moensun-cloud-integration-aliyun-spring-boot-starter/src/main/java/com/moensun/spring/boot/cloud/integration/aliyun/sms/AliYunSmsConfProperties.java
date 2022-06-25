package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliYunSmsConfProperties extends AliYunSmsProperties{
    private Boolean enabled;
    private Map<String,AliYunSmsInstanceProperties> instance;
}
