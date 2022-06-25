package com.moensun.spring.boot.cloud.integration.tencentcloud.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TencentCloudSmsInstanceProperties extends TencentCloudSmsProperties{
    private static final long serialVersionUID = 2080081711172955978L;
    private String name;
}
