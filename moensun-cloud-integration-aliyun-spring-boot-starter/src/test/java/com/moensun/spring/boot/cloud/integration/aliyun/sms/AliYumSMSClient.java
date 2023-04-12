package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations.SMSClient;
import com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations.TemplateCode;

@SMSClient(templateCodes = {
        @TemplateCode(key = "12",template = "333")
} )
public class AliYumSMSClient {
}
