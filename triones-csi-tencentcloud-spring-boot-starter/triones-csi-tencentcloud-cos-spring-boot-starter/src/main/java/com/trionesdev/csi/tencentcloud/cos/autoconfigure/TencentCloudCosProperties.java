package com.trionesdev.csi.tencentcloud.cos.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import static com.trionesdev.csi.tencentcloud.cos.autoconfigure.TencentCloudCosProperties.PREFIX;


@Data
@ConfigurationProperties(prefix = PREFIX)
public class TencentCloudCosProperties {
    public static final String PREFIX = "triones.tencentcloud.cos";
    private Boolean enabled;
    private String secretId;
    private String secretKey;
    private String region;
    private String bucket;
    private String urlPrefix;
}
