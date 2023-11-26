package com.moensun.spring.boot.cloud.integration.other.minio;

import com.moensun.csi.api.oss.OssTemplate;
import com.moensun.csi.minio.annotation.MinioClient;

@MinioClient(endpoint = "${minio.endpoint}",
        accessKey = "${minio.access-key}",secretKey = "${minio.secretKey}",bucket = "${minio.bucket}",urlPrefix = "${minio.urlPrefix}")
public interface DefaultMinioClient extends OssTemplate {
}
