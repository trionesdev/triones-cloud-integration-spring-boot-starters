package com.moensun.csi.huaweicloud.obs.autoconfigure;

import com.moensun.csi.huaweicloud.obs.HuaweiCloudObs;
import com.moensun.csi.huaweicloud.obs.HuaweiCloudObsConfig;
import com.obs.services.ObsClient;
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
@ConditionalOnProperty(prefix = "triones.huaweicloud.obs", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        HuaweiObsProperties.class
})
public class HuaweiCloudObsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private HuaweiObsProperties obsConfProperties;
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        ObsClient obsClient = new ObsClient(obsConfProperties.getAccessKeyId(), obsConfProperties.getSecretAccessKey(), obsConfProperties.getEndpoint());
        HuaweiCloudObsConfig huaweiCloudObsConfig = HuaweiCloudObsConfig.builder().bucket(obsConfProperties.getBucket()).urlPrefix(obsConfProperties.getUrlPrefix()).build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, obsClient);
        argumentValues.addIndexedArgumentValue(1, huaweiCloudObsConfig);
        registerBean(beanFactory, argumentValues, HuaweiCloudObs.class.getName());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.obsConfProperties = Binder.get(environment).bind("huaweicloud.obs", HuaweiObsProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(HuaweiCloudObs.class);
        beanDefinition.setBeanClassName(HuaweiCloudObs.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
