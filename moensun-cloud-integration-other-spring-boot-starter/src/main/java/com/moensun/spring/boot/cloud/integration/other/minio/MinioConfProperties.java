package com.moensun.spring.boot.cloud.integration.other.minio;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioConfProperties extends MinioProperties{
    private static final long serialVersionUID = 62874727035186991L;
    private Boolean enabled;
    private Map<String,MinioInstanceProperties> instance;
}
