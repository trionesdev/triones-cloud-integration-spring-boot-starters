package com.moensun.spring.boot.cloud.integration.qiniu;

import com.moensun.spring.boot.cloud.integration.qiniu.kodo.QiNiuKoDoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value = "com.moensun.spring.boot.cloud.integration.qiniu.QiNiuIntegrationConfiguration")
@Import(value = {
        QiNiuAutoConfiguration.class,
        QiNiuKoDoAutoConfiguration.class
})
public class QiNiuIntegrationConfiguration {
}
