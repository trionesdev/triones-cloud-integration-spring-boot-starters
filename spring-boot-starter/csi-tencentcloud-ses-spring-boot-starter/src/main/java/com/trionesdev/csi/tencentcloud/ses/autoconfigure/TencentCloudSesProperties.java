package com.trionesdev.csi.tencentcloud.ses.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.trionesdev.csi.tencentcloud.ses.autoconfigure.TencentCloudSesProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class TencentCloudSesProperties {
    public static final String PREFIX = "triones.tencentcloud.ses";
    private Boolean enabled;
    private String secretId;
    private String secretKey;
    private String endpoint;
    private String region;
    private String fromAddress;
    private String replyAddress;
    private Map<String,String> templateCodes;
}
