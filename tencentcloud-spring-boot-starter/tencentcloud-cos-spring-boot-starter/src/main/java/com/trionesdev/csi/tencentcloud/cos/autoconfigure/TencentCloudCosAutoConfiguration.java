package com.trionesdev.csi.tencentcloud.cos.autoconfigure;

import com.trionesdev.csi.tencentcloud.cos.TencentCloudCos;
import com.trionesdev.csi.tencentcloud.cos.TencentCloudCosConfig;
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

import static com.trionesdev.csi.tencentcloud.cos.autoconfigure.TencentCloudCosProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudCosProperties.class
})
public class TencentCloudCosAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private TencentCloudCosProperties tencentCloudCosProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        TencentCloudCosConfig tencentCloudCosConfig = TencentCloudCosConfig.builder()
                .accessKey(tencentCloudCosProperties.getAccessKey())
                .secretKey(tencentCloudCosProperties.getSecretKey())
                .region(tencentCloudCosProperties.getRegion())
                .bucket(tencentCloudCosProperties.getBucket())
                .urlPrefix(tencentCloudCosProperties.getUrlPrefix())
                .build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, tencentCloudCosConfig);
        registerBean(beanFactory, argumentValues, TencentCloudCos.class.getName());
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.tencentCloudCosProperties = Binder.get(environment).bind(PREFIX, TencentCloudCosProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(TencentCloudCos.class);
        beanDefinition.setBeanClassName(TencentCloudCos.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }


}
