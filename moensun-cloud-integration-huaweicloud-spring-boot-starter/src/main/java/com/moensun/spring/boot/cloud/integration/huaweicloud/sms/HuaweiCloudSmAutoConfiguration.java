package com.moensun.spring.boot.cloud.integration.huaweicloud.sms;

import com.moensun.cloud.integration.huaweicloud.sms.HuaweiCloudSms;
import com.moensun.cloud.integration.huaweicloud.sms.HuaweiCloudSmsClient;
import com.moensun.cloud.integration.huaweicloud.sms.HuaweiCloudSmsConfig;
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
@ConditionalOnProperty(prefix = "huaweicloud.sms", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {HuaweiCloudSmsConfProperties.class})
public class HuaweiCloudSmAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private HuaweiCloudSmsConfProperties confProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        Map<String, HuaweiCloudSmsInstanceProperties> instancesMap = confProperties.getInstance();
        if (MapUtils.isEmpty(instancesMap)) {
            HuaweiCloudSmsClient smsClient = new HuaweiCloudSmsClient(confProperties.getAppKey(), confProperties.getAppSecret());
            HuaweiCloudSmsConfig smsConfig = HuaweiCloudSmsConfig.builder()
                    .regionId(confProperties.getRegionId())
                    .signName(confProperties.getSignName())
                    .templateCodes(confProperties.getTemplateCodes())
                    .build();
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            argumentValues.addIndexedArgumentValue(0, smsClient);
            argumentValues.addIndexedArgumentValue(1, smsConfig);
            registerBean(beanFactory, argumentValues, HuaweiCloudSms.class.getName());
        } else {
            instancesMap.forEach((k, v) -> {
                HuaweiCloudSmsClient smsClient = new HuaweiCloudSmsClient(v.getAppKey(), v.getAppSecret());
                HuaweiCloudSmsConfig smsConfig = HuaweiCloudSmsConfig.builder()
                        .regionId(v.getRegionId())
                        .signName(v.getSignName())
                        .templateCodes(v.getTemplateCodes())
                        .build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
                argumentValues.addIndexedArgumentValue(0, smsClient);
                argumentValues.addIndexedArgumentValue(1, smsConfig);
                registerBean(beanFactory, argumentValues, baneName);
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.confProperties = Binder.get(environment).bind("huaweicloud.sms", HuaweiCloudSmsConfProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(HuaweiCloudSms.class);
        beanDefinition.setBeanClassName(HuaweiCloudSms.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
