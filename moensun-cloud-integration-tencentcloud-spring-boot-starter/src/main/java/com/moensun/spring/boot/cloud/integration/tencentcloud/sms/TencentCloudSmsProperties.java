package com.moensun.spring.boot.cloud.integration.tencentcloud.sms;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class TencentCloudSmsProperties implements Serializable {
    private static final long serialVersionUID = -8817279247973141793L;
    private String secretId;
    private String secretKey;
    private String sdkAppId;
    private String signName;
    private Map<String,String> templateCodes;
}
