package com.moensun.spring.boot.cloud.integration.huaweicloud.obs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "huaweicloud.obs")
public class HuaweiObsConfProperties extends HuaweiObsProperties{
    private Boolean enabled;
    private Map<String,HuaweiObsInstanceProperties> instance;
}
