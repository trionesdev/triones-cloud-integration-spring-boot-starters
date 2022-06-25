package com.moensun.spring.boot.cloud.integration.aliyun.oss;

import lombok.Data;

@Data
public class AliYunOssProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucket;
    private String urlPrefix;
}
