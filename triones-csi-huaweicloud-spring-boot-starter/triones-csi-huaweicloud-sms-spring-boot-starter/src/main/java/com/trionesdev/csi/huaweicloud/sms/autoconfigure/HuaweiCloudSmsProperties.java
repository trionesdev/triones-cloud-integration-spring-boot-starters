package com.trionesdev.csi.huaweicloud.sms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.trionesdev.csi.huaweicloud.sms.autoconfigure.HuaweiCloudSmsProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class HuaweiCloudSmsProperties {
    public static final String PREFIX = "triones.huaweicloud.sms";
    private Boolean enabled;
    private String appKey;
    private String appSecret;
    private String regionId;
    private String signName;
    private String sender;
    private Map<String, String> templateCodes;

}
