package com.trionesdev.csi.minio.autoconfigure;

import com.trionesdev.csi.minio.MinioCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.minio.autoconfigure.MinioProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class MinioProperties extends MinioCredentials {
    public static final String PREFIX = "triones.minio";
    private Boolean enabled;
}
