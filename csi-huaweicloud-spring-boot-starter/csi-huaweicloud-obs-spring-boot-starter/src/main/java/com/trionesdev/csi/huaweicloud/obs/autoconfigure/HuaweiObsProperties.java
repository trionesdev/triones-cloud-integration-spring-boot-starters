package com.trionesdev.csi.huaweicloud.obs.autoconfigure;

import com.trionesdev.csi.huaweicloud.obs.HuaweiCloudObsCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.huaweicloud.obs.autoconfigure.HuaweiObsProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class HuaweiObsProperties extends HuaweiCloudObsCredentials {
    public static final String PREFIX = "triones.huaweicloud.obs";
    private Boolean enabled;
}
