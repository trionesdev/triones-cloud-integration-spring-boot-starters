package com.trionesdev.csi.azure.blob.autoconfigure;

import com.trionesdev.csi.azure.blob.AzureBlobCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.azure.blob.autoconfigure.AzureBlobProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class AzureBlobProperties extends AzureBlobCredentials {
    public static final String PREFIX = "triones.azure.blob";
    private Boolean enabled;
}
