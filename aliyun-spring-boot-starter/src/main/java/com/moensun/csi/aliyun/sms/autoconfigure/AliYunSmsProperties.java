package com.moensun.csi.aliyun.sms.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliYunSmsProperties {
    private Boolean enabled;
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
    private String signName;
    private Map<String,String> templateCodes;

}
