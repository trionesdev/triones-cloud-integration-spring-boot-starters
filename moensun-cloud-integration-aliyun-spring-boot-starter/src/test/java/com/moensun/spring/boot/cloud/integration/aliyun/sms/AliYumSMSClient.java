package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations.AliYunSMSClient;
import com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations.TemplateCode;

@AliYunSMSClient(templateCodes = {
        @TemplateCode(key = "12",template = "333")
} )
public class AliYumSMSClient {
}
