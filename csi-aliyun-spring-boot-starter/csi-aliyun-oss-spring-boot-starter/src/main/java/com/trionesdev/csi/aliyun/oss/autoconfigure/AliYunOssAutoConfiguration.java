package com.trionesdev.csi.aliyun.oss.autoconfigure;

import com.trionesdev.csi.aliyun.oss.AliYunOSS;
import com.trionesdev.csi.aliyun.oss.AliYunOssConfig;
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

import static com.trionesdev.csi.aliyun.oss.autoconfigure.AliYunOssProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        AliYunOssProperties.class
})
public class AliYunOssAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private AliYunOssProperties ossProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        AliYunOssConfig aliYunOssProperties = AliYunOssConfig.builder()
                .accessKeyId(ossProperties.getAccessKeyId())
                .accessKeySecret(ossProperties.getAccessKeySecret())
                .endpoint(ossProperties.getEndpoint())
                .bucket(ossProperties.getBucket())
                .urlPrefix(ossProperties.getUrlPrefix())
                .build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, aliYunOssProperties);
        registerBean(beanFactory, argumentValues, AliYunOSS.class.getName());

    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.ossProperties = Binder.get(environment).bind(PREFIX, AliYunOssProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AliYunOSS.class);
        beanDefinition.setBeanClassName(AliYunOSS.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
