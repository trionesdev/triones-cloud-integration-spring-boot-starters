package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.moensun.csi.aliyun.sms.annotation.AliYunSMSClient;
import com.moensun.csi.aliyun.sms.annotation.TemplateCode;

@AliYunSMSClient(templateCodes = {
        @TemplateCode(key = "12",template = "333")
} )
public class AliYumSMSClient {
}
