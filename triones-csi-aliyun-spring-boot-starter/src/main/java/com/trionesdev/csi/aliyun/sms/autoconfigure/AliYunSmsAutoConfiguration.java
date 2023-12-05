package com.trionesdev.csi.aliyun.sms.autoconfigure;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.trionesdev.csi.aliyun.sms.AliYunSms;
import com.trionesdev.csi.aliyun.sms.AliYunSmsConfig;
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
@ConditionalOnProperty(prefix = "triones.aliyun.sms", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {AliYunSmsProperties.class})
public class AliYunSmsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private AliYunSmsProperties aliYunSmsProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        DefaultProfile profile = DefaultProfile.getProfile(aliYunSmsProperties.getRegionId(), aliYunSmsProperties.getAccessKeyId(), aliYunSmsProperties.getAccessKeySecret());
        AliYunSmsConfig aliYunSmsConfig = AliYunSmsConfig.builder()
                .regionId(aliYunSmsProperties.getRegionId())
                .signName(aliYunSmsProperties.getSignName())
                .templateCodes(aliYunSmsProperties.getTemplateCodes())
                .build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, new DefaultAcsClient(profile));
        argumentValues.addIndexedArgumentValue(1, aliYunSmsConfig);
        registerBean(beanFactory, argumentValues, AliYunSms.class.getName());

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.aliYunSmsProperties = Binder.get(environment).bind("triones.aliyun.sms", AliYunSmsProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AliYunSms.class);
        beanDefinition.setBeanClassName(AliYunSms.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
