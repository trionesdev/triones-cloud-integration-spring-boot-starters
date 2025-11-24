package com.trionesdev.csi.minio.autoconfigure;

import com.trionesdev.csi.minio.Minio;
import com.trionesdev.csi.minio.MinioConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.minio.autoconfigure.MinioProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        MinioProperties.class
})
public class MinioAutoConfiguration {
    private final MinioProperties minioProperties;

    @Bean
    public Minio minio() {
        MinioConfig minioConfig = MinioConfig.builder()
                .accessKey(minioProperties.getAccessKey())
                .secretKey(minioProperties.getSecretKey())
                .endpoint(minioProperties.getEndpoint())
                .bucket(minioProperties.getBucket())
                .urlPrefix(minioProperties.getUrlPrefix())
                .build();
        return new Minio(minioConfig);
    }

}
