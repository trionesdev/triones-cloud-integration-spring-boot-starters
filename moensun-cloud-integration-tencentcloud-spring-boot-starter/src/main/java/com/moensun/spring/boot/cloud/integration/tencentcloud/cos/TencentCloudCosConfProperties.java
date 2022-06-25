package com.moensun.spring.boot.cloud.integration.tencentcloud.cos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "tencentcloud.cos")
public class TencentCloudCosConfProperties extends TencentCloudCosProperties{
    private static final long serialVersionUID = 6665603315958275664L;
    private Boolean enabled;
    private Map<String,TencentCloudCosInstanceProperties> instance;
}
