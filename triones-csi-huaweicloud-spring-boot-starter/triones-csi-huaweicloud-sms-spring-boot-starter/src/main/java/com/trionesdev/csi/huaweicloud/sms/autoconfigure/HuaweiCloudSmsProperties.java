package com.trionesdev.csi.huaweicloud.sms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "triones.huaweicloud.sms")
public class HuaweiCloudSmsProperties {
    private Boolean enabled;
    private String appKey;
    private String appSecret;
    private String regionId;
    private String signName;
    private String sender;
    private Map<String, String> templateCodes;

}
