package com.trionesdev.csi.minio.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

import static com.trionesdev.csi.minio.autoconfigure.MinioProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class MinioProperties implements Serializable {
    public static final String PREFIX = "triones.minio";
    private Boolean enabled;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String urlPrefix;

}
