package com.moensun.spring.boot.cloud.integration.huaweicloud.obs;

import lombok.Data;

@Data
public class HuaweiObsProperties {
    private String accessKeyId;
    private String secretAccessKey;
    private String endpoint;
    private String bucket;
    private String urlPrefix;
}
