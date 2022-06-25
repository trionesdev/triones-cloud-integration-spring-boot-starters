package com.moensun.spring.boot.cloud.integration.other.minio;

import com.moensun.cloud.integration.minio.Minio;
import com.moensun.cloud.integration.minio.MinioConfig;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "minio", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        MinioConfProperties.class
})
public class MinioAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private MinioConfProperties minioConfProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        Map<String, MinioInstanceProperties> instance = minioConfProperties.getInstance();
        if (MapUtils.isEmpty(instance)) {
            MinioClient minioClient = MinioClient.builder().endpoint(minioConfProperties.getEndpoint())
                    .credentials(minioConfProperties.getAccessKey(), minioConfProperties.getSecretKey())
                    .build();
            MinioConfig minioConfig = MinioConfig.builder()
                    .bucket(minioConfProperties.getBucket())
                    .urlPrefix(minioConfProperties.getUrlPrefix())
                    .build();
            registerBean(beanFactory, minioClient, minioConfig, Minio.class.getName());
        } else {
            instance.forEach((k, v) -> {
                MinioClient minioClient = MinioClient.builder().endpoint(v.getEndpoint())
                        .credentials(v.getAccessKey(), v.getSecretKey())
                        .build();
                MinioConfig minioConfig = MinioConfig.builder()
                        .bucket(v.getBucket())
                        .urlPrefix(v.getUrlPrefix())
                        .build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                registerBean(beanFactory, minioClient, minioConfig, baneName);
            });
        }
    }


    @Override
    public void setEnvironment(Environment environment) {
        minioConfProperties = Binder.get(environment).bind("minio", MinioConfProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, MinioClient minioClient, MinioConfig minioConfig, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(Minio.class);
        beanDefinition.setBeanClassName(Minio.class.getName());
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, minioClient);
        argumentValues.addIndexedArgumentValue(1, minioConfig);
        beanDefinition.setConstructorArgumentValues(argumentValues);

        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
