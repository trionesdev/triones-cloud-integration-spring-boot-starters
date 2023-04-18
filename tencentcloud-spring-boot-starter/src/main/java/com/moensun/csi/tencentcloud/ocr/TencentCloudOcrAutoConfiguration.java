package com.moensun.csi.tencentcloud.ocr;

import com.moensun.csi.api.ocr.OcrTemplate;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "tencentcloud.ocr", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudOcrConfProperties.class
})
public class TencentCloudOcrAutoConfiguration {
    private final TencentCloudOcrConfProperties tencentCloudOcrConfProperties;

    @Bean
    public OcrClient ocrClient() {
        Credential cred = new Credential(tencentCloudOcrConfProperties.getSecretId(), tencentCloudOcrConfProperties.getSecretKey());
        return new OcrClient(cred, tencentCloudOcrConfProperties.getRegion());
    }

    @Bean
    public OcrTemplate ocrTemplate(OcrClient client) {
        return new TencentCloudOcr(client);
    }

}
