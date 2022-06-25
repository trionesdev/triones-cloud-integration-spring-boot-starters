package com.moensun.spring.boot.cloud.integration.tencentcloud.cos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TencentCloudCosInstanceProperties extends TencentCloudCosProperties{
    private static final long serialVersionUID = 6557635695436752968L;
    private String name;
}
