package com.moensun.spring.boot.cloud.integration.aliyun.oss;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AliYunOssInstanceProperties extends AliYunOssProperties{
    private String name;
}
