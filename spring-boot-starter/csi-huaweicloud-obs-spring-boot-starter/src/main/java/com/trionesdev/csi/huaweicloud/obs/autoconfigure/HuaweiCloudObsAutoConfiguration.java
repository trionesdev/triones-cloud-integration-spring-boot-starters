package com.trionesdev.csi.huaweicloud.obs.autoconfigure;

import com.trionesdev.csi.huaweicloud.obs.HuaweiCloudObs;
import com.trionesdev.csi.huaweicloud.obs.HuaweiCloudObsConfig;
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

import static com.trionesdev.csi.huaweicloud.obs.autoconfigure.HuaweiObsProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        HuaweiObsProperties.class
})
public class HuaweiCloudObsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private HuaweiObsProperties obsConfProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        HuaweiCloudObsConfig huaweiCloudObsConfig = HuaweiCloudObsConfig.builder()
                .accessKeyId(obsConfProperties.getAccessKeyId())
                .secretAccessKey(obsConfProperties.getSecretAccessKey())
                .endpoint(obsConfProperties.getEndpoint())
                .bucket(obsConfProperties.getBucket()).urlPrefix(obsConfProperties.getUrlPrefix()).build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, huaweiCloudObsConfig);
        registerBean(beanFactory, argumentValues, HuaweiCloudObs.class.getName());
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.obsConfProperties = Binder.get(environment).bind(PREFIX, HuaweiObsProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(HuaweiCloudObs.class);
        beanDefinition.setBeanClassName(HuaweiCloudObs.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
