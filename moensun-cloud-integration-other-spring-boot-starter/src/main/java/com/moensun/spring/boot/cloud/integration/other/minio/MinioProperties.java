package com.moensun.spring.boot.cloud.integration.other.minio;

import lombok.Data;

import java.io.Serializable;

@Data
public class MinioProperties implements Serializable {
    private static final long serialVersionUID = -561801099900291578L;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String urlPrefix;
}
