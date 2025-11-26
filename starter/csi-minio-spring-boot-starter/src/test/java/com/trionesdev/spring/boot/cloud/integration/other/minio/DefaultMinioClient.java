package com.trionesdev.spring.boot.cloud.integration.other.minio;

import com.trionesdev.csi.api.oss.OssTemplate;
import com.trionesdev.csi.minio.annotation.MinioClient;

@MinioClient(endpoint = "${minio.endpoint}",
        accessKey = "${minio.access-key}",secretKey = "${minio.secretKey}",bucket = "${minio.bucket}",urlPrefix = "${minio.urlPrefix}")
public interface DefaultMinioClient extends OssTemplate {
}
