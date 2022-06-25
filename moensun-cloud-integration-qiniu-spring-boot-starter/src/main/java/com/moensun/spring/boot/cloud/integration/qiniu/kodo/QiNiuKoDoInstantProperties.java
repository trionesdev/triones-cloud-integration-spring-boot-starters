package com.moensun.spring.boot.cloud.integration.qiniu.kodo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QiNiuKoDoInstantProperties extends QiNiuKoDoProperties{
    private static final long serialVersionUID = 4362586696036836595L;
    private String name;
}
