package com.trionesdev.csi.huaweicloud.sms.autoconfigure;

import com.trionesdev.csi.huaweicloud.sms.HuaweiCloudSms;
import com.trionesdev.csi.huaweicloud.sms.HuaweiCloudSmsClient;
import com.trionesdev.csi.huaweicloud.sms.HuaweiCloudSmsConfig;
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
@ConditionalOnProperty(prefix = "triones.huaweicloud.sms", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {HuaweiCloudSmsProperties.class})
public class HuaweiCloudSmAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private HuaweiCloudSmsProperties confProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
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

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.confProperties = Binder.get(environment).bind("triones.huaweicloud.sms", HuaweiCloudSmsProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(HuaweiCloudSms.class);
        beanDefinition.setBeanClassName(HuaweiCloudSms.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }


}
