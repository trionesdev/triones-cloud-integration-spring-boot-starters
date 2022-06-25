package com.moensun.spring.boot.cloud.integration.other;

import com.moensun.spring.boot.cloud.integration.other.minio.MinioAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(value = "com.moensun.spring.boot.cloud.integration.other.OtherIntegrationConfiguration")
@Import(value = {
        MinioAutoConfiguration.class
})
public class OtherIntegrationConfiguration {
}
