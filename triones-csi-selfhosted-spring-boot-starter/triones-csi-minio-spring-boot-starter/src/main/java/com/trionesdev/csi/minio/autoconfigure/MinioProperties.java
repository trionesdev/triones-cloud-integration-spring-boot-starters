package com.trionesdev.csi.minio.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "triones.minio")
public class MinioProperties implements Serializable {
    private Boolean enabled;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String urlPrefix;

}
