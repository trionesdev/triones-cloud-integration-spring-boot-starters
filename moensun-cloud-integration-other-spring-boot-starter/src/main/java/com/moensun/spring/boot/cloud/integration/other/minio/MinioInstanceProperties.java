package com.moensun.spring.boot.cloud.integration.other.minio;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MinioInstanceProperties extends MinioProperties{
    private static final long serialVersionUID = -7770285947224868945L;
    private String name;
}
