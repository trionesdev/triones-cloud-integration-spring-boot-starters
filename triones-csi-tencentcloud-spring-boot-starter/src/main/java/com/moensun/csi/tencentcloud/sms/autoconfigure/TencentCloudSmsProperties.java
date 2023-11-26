package com.moensun.csi.tencentcloud.sms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "triones.tencentcloud.sms")
public class TencentCloudSmsProperties {
    private Boolean enabled;
    private String secretId;
    private String secretKey;
    private String sdkAppId;
    private String signName;
    private Map<String,String> templateCodes;
}
