package com.trionesdev.csi.qiniu.kodo.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.trionesdev.csi.qiniu.kodo.autoconfigure.QiNiuKoDoProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class QiNiuKoDoProperties extends QiNiuProperties{
    public static final String PREFIX = "triones.qiniu.kodo";
    private Boolean enabled;
    private String bucket;
    private String urlPrefix;
}
