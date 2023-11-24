package com.moensun.csi.tencentcloud.cos.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tencentcloud.cos")
public class TencentCloudCosProperties {
    private Boolean enabled;
    private String secretId;
    private String secretKey;
    private String region;
    private String bucket;
    private String urlPrefix;
}
