package com.trionesdev.csi.aliyun.oss.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.aliyun.oss.autoconfigure.AliYunOssProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class AliYunOssProperties {
    public static final String PREFIX = "triones.aliyun.oss";
    private Boolean enabled;
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucket;
    private String urlPrefix;

}
