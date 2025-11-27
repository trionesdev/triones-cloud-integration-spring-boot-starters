package com.trionesdev.csi.tencentcloud.ocr.autoconfigure;

import com.trionesdev.csi.tencentcloud.ocr.TenantCloudOcrCredentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;

import static com.trionesdev.csi.tencentcloud.ocr.autoconfigure.TencentCloudOcrProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class TencentCloudOcrProperties extends TenantCloudOcrCredentials implements Serializable {
    @Serial
    private static final long serialVersionUID = -106547741259688219L;
    public static final String PREFIX = "triones.tencentcloud.ocr";
    private Boolean enabled;
}
