package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.moensun.aliyun.sms.autoconfigure.AliYunSMSClient;
import com.moensun.aliyun.sms.autoconfigure.TemplateCode;

@AliYunSMSClient(templateCodes = {
        @TemplateCode(key = "12",template = "333")
} )
public class AliYumSMSClient {
}
