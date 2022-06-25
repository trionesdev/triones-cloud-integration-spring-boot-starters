package com.moensun.spring.boot.cloud.integration.qiniu.kodo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "qiniu.kodo")
public class QiNiuKoDoConfProperties extends QiNiuKoDoProperties{
    private static final long serialVersionUID = -3241933552582021789L;
    private Boolean enabled;
    private Map<String,QiNiuKoDoInstantProperties> instance;
}
