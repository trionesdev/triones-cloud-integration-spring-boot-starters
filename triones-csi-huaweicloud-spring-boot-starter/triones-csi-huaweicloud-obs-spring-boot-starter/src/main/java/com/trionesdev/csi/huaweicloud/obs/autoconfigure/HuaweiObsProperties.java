package com.trionesdev.csi.huaweicloud.obs.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.huaweicloud.obs.autoconfigure.HuaweiObsProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class HuaweiObsProperties {
    public static final String PREFIX = "triones.huaweicloud.obs";
    private Boolean enabled;
    private String accessKeyId;
    private String secretAccessKey;
    private String endpoint;
    private String bucket;
    private String urlPrefix;

}
