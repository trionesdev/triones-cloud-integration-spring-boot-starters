package com.trionesdev.csi.qiniu.kodo.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "triones.qiniu.kodo")
public class QiNiuKoDoProperties extends QiNiuProperties{
    private Boolean enabled;
    private String bucket;
    private String urlPrefix;
}
