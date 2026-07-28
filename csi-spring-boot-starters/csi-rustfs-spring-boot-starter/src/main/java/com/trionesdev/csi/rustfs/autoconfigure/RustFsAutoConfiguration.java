package com.trionesdev.csi.rustfs.autoconfigure;

import com.trionesdev.csi.rustfs.RustFs;
import com.trionesdev.csi.rustfs.RustFsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.rustfs.autoconfigure.RustFsProperties.PREFIX;


@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        RustFsProperties.class
})
public class RustFsAutoConfiguration {
    private final RustFsProperties rustFsProperties;

    @Bean
    public RustFs minio() {
        RustFsConfig rustFsConfig = RustFsConfig.builder()
                .accessKeyId(rustFsProperties.getAccessKeyId())
                .secretAccessKey(rustFsProperties.getSecretAccessKey())
                .endpoint(rustFsProperties.getEndpoint())
                .bucket(rustFsProperties.getBucket())
                .urlPrefix(rustFsProperties.getUrlPrefix())
                .build();
        return new RustFs(rustFsConfig);
    }

}
