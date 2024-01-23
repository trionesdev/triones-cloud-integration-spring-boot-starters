package com.trionesdev.csi.tencentcloud.ocr.autoconfigure;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.trionesdev.csi.api.ocr.OcrTemplate;
import com.trionesdev.csi.tencentcloud.ocr.TencentCloudOcr;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.tencentcloud.ocr.autoconfigure.TencentCloudOcrConfProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
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
