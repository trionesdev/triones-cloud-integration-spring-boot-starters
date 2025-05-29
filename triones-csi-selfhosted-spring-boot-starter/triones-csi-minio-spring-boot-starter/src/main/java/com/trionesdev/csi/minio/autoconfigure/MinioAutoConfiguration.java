package com.trionesdev.csi.minio.autoconfigure;

import com.trionesdev.csi.minio.Minio;
import com.trionesdev.csi.minio.MinioConfig;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

import static com.trionesdev.csi.minio.autoconfigure.MinioProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        MinioProperties.class
})
public class MinioAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private MinioProperties minioProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        MinioConfig minioConfig = MinioConfig.builder()
                .accessKey(minioProperties.getAccessKey())
                .secretKey(minioProperties.getSecretKey())
                .endpoint(minioProperties.getEndpoint())
                .bucket(minioProperties.getBucket())
                .urlPrefix(minioProperties.getUrlPrefix())
                .build();
        registerBean(beanFactory, minioConfig, Minio.class.getName());
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        minioProperties = Binder.get(environment).bind(PREFIX, MinioProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, MinioConfig minioConfig, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(Minio.class);
        beanDefinition.setBeanClassName(Minio.class.getName());
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(1, minioConfig);
        beanDefinition.setConstructorArgumentValues(argumentValues);

        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
