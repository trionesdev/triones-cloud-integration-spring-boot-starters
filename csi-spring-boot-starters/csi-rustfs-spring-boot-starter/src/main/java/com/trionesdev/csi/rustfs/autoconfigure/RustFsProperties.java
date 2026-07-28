package com.trionesdev.csi.rustfs.autoconfigure;

import com.trionesdev.csi.rustfs.RustFsCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = RustFsProperties.PREFIX)
public class RustFsProperties extends RustFsCredentials {
    public static final String PREFIX = "triones.rustfs";
    private Boolean enabled;
}
