package com.trionesdev.csi.tencentcloud.ocr.autoconfigure;

import com.trionesdev.csi.api.ocr.OcrTemplate;
import com.trionesdev.csi.tencentcloud.ocr.TenantCloudOcrConfig;
import com.trionesdev.csi.tencentcloud.ocr.TencentCloudOcr;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.tencentcloud.ocr.autoconfigure.TencentCloudOcrProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudOcrProperties.class
})
public class TencentCloudOcrAutoConfiguration {
    private final TencentCloudOcrProperties ocrProperties;

    @Bean
    public OcrTemplate ocrTemplate() {
        TenantCloudOcrConfig config = TenantCloudOcrConfig.builder()
                .secretId(ocrProperties.getSecretId())
                .secretKey(ocrProperties.getSecretKey())
                .region(ocrProperties.getRegion())
                .build();
        return new TencentCloudOcr(config);
    }

}
