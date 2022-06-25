package com.moensun.spring.boot.cloud.integration.qiniu.kodo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QiNiuKoDoProperties implements Serializable {
    private static final long serialVersionUID = 6065315397144893511L;
    private String bucket;
    private String urlPrefix;
}
