package com.trionesdev.csi.tencentcloud.cos.autoconfigure;

import com.trionesdev.csi.tencentcloud.cos.TencentCloudCosCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import static com.trionesdev.csi.tencentcloud.cos.autoconfigure.TencentCloudCosProperties.PREFIX;


@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class TencentCloudCosProperties extends TencentCloudCosCredentials {
    public static final String PREFIX = "triones.tencentcloud.cos";
    private Boolean enabled;
}
