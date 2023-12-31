package com.trionesdev.csi.tencentcloud.ocr.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "triones.tencentcloud.ocr")
public class TencentCloudOcrConfProperties implements Serializable {
    private static final long serialVersionUID = -106547741259688219L;
    private Boolean enabled;
    private String secretId;
    private String secretKey;
    private String region;
}
