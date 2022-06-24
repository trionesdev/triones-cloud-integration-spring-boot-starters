package com.moensun.spring.boot.cloud.integration.huaweicloud.obs;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HuaweiObsInstanceProperties extends HuaweiObsProperties {
    private String name;
}
