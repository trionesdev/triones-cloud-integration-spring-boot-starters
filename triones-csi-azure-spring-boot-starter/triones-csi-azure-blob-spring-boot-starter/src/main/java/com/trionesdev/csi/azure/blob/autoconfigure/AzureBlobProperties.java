package com.trionesdev.csi.azure.blob.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.azure.blob.autoconfigure.AzureBlobProperties.PREFIX;

@Data
@ConfigurationProperties(prefix = PREFIX)
public class AzureBlobProperties {
    public static final String PREFIX = "triones.azure.blob";
    private Boolean enabled;
    private String connectionString;
    private String containerName;
    private String urlPrefix;

}
