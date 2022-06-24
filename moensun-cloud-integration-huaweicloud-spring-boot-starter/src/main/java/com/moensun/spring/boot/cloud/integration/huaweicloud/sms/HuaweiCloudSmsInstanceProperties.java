package com.moensun.spring.boot.cloud.integration.huaweicloud.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HuaweiCloudSmsInstanceProperties extends HuaweiCloudSmsProperties{
    private String name; //实例名称
}
