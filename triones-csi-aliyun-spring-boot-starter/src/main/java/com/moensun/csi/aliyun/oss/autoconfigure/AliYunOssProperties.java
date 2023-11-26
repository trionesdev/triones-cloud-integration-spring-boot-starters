package com.moensun.csi.aliyun.oss.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "triones.aliyun.oss")
public class AliYunOssProperties {
    private Boolean enabled;
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucket;
    private String urlPrefix;

}
