package com.trionesdev.csi.tencentcloud.sms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.trionesdev.csi.tencentcloud.sms.autoconfigure.TencentCloudSmsProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class TencentCloudSmsProperties {
    public static final String PREFIX = "triones.tencentcloud.sms";
    private Boolean enabled;
    private String secretId;
    private String secretKey;
    private String sdkAppId;
    private String signName;
    private Map<String,String> templateCodes;
}
