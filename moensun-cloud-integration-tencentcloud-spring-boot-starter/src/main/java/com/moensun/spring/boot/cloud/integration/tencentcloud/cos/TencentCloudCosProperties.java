package com.moensun.spring.boot.cloud.integration.tencentcloud.cos;

import lombok.Data;

import java.io.Serializable;

@Data
public class TencentCloudCosProperties implements Serializable {
    private static final long serialVersionUID = 2092549637233506937L;
    private String secretId;
    private String secretKey;
    private String region;
    private String bucket;
    private String urlPrefix;
}
