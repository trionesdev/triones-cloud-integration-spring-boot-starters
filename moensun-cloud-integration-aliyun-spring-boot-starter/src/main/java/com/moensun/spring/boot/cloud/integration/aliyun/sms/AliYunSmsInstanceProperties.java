package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AliYunSmsInstanceProperties extends AliYunSmsProperties{
    private String name; //实例名称
}
