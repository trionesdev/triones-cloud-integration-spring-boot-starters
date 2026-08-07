package com.trionesdev.csi.localstorage.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.localstorage.autoconfigure.LocalStorageProperties.PREFIX;


@Data
@ConfigurationProperties(prefix = PREFIX)
public class LocalStorageProperties {
    public static final String PREFIX = "triones.localstorage";
    private Boolean enabled;
    private String dir;
    private String bucket;
    private String urlPrefix;
}
