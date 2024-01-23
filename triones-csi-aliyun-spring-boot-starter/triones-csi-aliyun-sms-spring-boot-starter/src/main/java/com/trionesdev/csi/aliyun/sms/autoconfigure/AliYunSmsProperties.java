package com.trionesdev.csi.aliyun.sms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.trionesdev.csi.aliyun.sms.autoconfigure.AliYunSmsProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class AliYunSmsProperties {
    public static final String PREFIX = "triones.aliyun.sms";
    private Boolean enabled;
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
    private String signName;
    private Map<String,String> templateCodes;

}
