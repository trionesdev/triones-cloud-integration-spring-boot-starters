package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import lombok.Data;

import java.util.Map;

@Data
public class AliYunSmsProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
    private String signName;
    private Map<String,String> templateCodes;
}
