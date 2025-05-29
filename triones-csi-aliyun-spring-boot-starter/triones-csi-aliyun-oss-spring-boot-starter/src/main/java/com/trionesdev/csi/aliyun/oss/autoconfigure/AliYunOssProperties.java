package com.trionesdev.csi.aliyun.oss.autoconfigure;

import com.trionesdev.csi.aliyun.oss.AliYunOssCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.aliyun.oss.autoconfigure.AliYunOssProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class AliYunOssProperties extends AliYunOssCredentials {
    public static final String PREFIX = "triones.aliyun.oss";
    private Boolean enabled;

}
