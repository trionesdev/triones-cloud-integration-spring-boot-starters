package com.moensun.spring.boot.cloud.integration.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuConfProperties implements Serializable {
    private static final long serialVersionUID = 6141972667319639235L;
    private Boolean enabled;
    private String accessKey;
    private String secretKey;
}
