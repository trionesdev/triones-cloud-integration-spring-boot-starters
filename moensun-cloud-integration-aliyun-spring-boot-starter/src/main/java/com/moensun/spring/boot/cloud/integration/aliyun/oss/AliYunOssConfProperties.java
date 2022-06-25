package com.moensun.spring.boot.cloud.integration.aliyun.oss;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliYunOssConfProperties extends AliYunOssProperties{
    private Boolean enabled;
    private Map<String,AliYunOssInstanceProperties> instance;
}
