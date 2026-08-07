package com.trionesdev.csi.localstorage.autoconfigure;

import com.trionesdev.csi.localstorage.LocalStorage;
import com.trionesdev.csi.localstorage.LocalStorageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.localstorage.autoconfigure.LocalStorageProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        LocalStorageProperties.class
})
public class LocalStorageAutoConfiguration {
    private final LocalStorageProperties localStorageProperties;

    @Bean
    public LocalStorage localStorage() {
        LocalStorageConfig config = LocalStorageConfig.builder()
                .dir(localStorageProperties.getDir())
                .bucket(localStorageProperties.getBucket())
                .urlPrefix(localStorageProperties.getUrlPrefix())
                .build();
        return new LocalStorage(config);
    }
}
