package com.moensun.csi.huaweicloud.obs.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "triones.huaweicloud.obs")
public class HuaweiObsProperties {
    private Boolean enabled;
    private String accessKeyId;
    private String secretAccessKey;
    private String endpoint;
    private String bucket;
    private String urlPrefix;

}
