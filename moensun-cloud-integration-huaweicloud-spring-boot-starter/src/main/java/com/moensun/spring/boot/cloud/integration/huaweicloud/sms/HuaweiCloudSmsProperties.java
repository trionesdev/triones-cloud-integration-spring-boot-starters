package com.moensun.spring.boot.cloud.integration.huaweicloud.sms;

import lombok.Data;

import java.util.Map;

@Data
public class HuaweiCloudSmsProperties {
    private String appKey;
    private String appSecret;
    private String regionId;
    private String signName;
    private Map<String,String> templateCodes;
}
