package com.moensun.spring.boot.cloud.integration.huaweicloud.obs;

import com.moensun.cloud.integration.huaweicloud.obs.HuaweiCloudObs;
import com.moensun.cloud.integration.huaweicloud.obs.HuaweiCloudObsConfig;
import com.obs.services.ObsClient;
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
@ConditionalOnProperty(prefix = "huaweicloud.obs", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        HuaweiObsConfProperties.class
})
public class HuaweiCloudObsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private HuaweiObsConfProperties obsConfProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        Map<String, HuaweiObsInstanceProperties> instancePropertiesMap = obsConfProperties.getInstance();
        if (MapUtils.isEmpty(instancePropertiesMap)) {
            ObsClient obsClient = new ObsClient(obsConfProperties.getAccessKeyId(), obsConfProperties.getSecretAccessKey(), obsConfProperties.getEndpoint());
            HuaweiCloudObsConfig huaweiCloudObsConfig = HuaweiCloudObsConfig.builder().bucket(obsConfProperties.getBucket()).urlPrefix(obsConfProperties.getUrlPrefix()).build();
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            argumentValues.addIndexedArgumentValue(0, obsClient);
            argumentValues.addIndexedArgumentValue(1, huaweiCloudObsConfig);
            registerBean(beanFactory, argumentValues, HuaweiCloudObs.class.getName());
        } else {
            instancePropertiesMap.forEach((k, v) -> {
                ObsClient obsClient = new ObsClient(v.getAccessKeyId(), v.getSecretAccessKey(), v.getEndpoint());
                HuaweiCloudObsConfig huaweiCloudObsConfig = HuaweiCloudObsConfig.builder().bucket(v.getBucket()).urlPrefix(v.getUrlPrefix()).build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
                argumentValues.addIndexedArgumentValue(0, obsClient);
                argumentValues.addIndexedArgumentValue(1, huaweiCloudObsConfig);
                registerBean(beanFactory, argumentValues, baneName);
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.obsConfProperties = Binder.get(environment).bind("huaweicloud.obs", HuaweiObsConfProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(HuaweiCloudObs.class);
        beanDefinition.setBeanClassName(HuaweiCloudObs.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
