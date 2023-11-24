package com.moensun.csi.qiniu.kodo.autoconfigure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class QiNiuProperties {
    private String accessKey;
    private String secretKey;
}
