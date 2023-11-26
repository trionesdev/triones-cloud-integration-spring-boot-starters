package com.moensun.csi.aliyun.oss.autoconfigure;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.moensun.csi.aliyun.oss.AliYunOSS;
import com.moensun.csi.aliyun.oss.AliYunOssConfig;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "triones.aliyun.oss", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        AliYunOssProperties.class
})
public class AliYunOssAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private AliYunOssProperties ossProperties;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        OSS oss = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        AliYunOssConfig aliYunOssProperties = AliYunOssConfig.builder()
                .bucket(ossProperties.getBucket())
                .urlPrefix(ossProperties.getUrlPrefix())
                .build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, oss);
        argumentValues.addIndexedArgumentValue(1, aliYunOssProperties);
        registerBean(beanFactory,argumentValues, AliYunOSS.class.getName());

    }

    @Override
    public void setEnvironment( Environment environment) {
        this.ossProperties =  Binder.get(environment).bind("aliyun.oss", AliYunOssProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AliYunOSS.class);
        beanDefinition.setBeanClassName(AliYunOSS.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
