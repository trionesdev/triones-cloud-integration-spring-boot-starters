package com.moensun.spring.boot.cloud.integration.aliyun.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.moensun.cloud.integration.aliyun.sms.AliYunSms;
import com.moensun.cloud.integration.aliyun.sms.AliYunSmsConfig;
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
@ConditionalOnProperty(prefix = "aliyun.sms", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {AliYunSmsConfProperties.class})
public class AliYunSmsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private AliYunSmsConfProperties aliYunSmsConfProperties;

    @Override
    public void postProcessBeanFactory( ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        Map<String, AliYunSmsInstanceProperties> instancesMap = aliYunSmsConfProperties.getInstance();
        if (MapUtils.isEmpty(instancesMap)) {
            DefaultProfile profile = DefaultProfile.getProfile(aliYunSmsConfProperties.getRegionId(), aliYunSmsConfProperties.getAccessKeyId(), aliYunSmsConfProperties.getAccessKeySecret());
            AliYunSmsConfig aliYunSmsConfig = AliYunSmsConfig.builder()
                    .regionId(aliYunSmsConfProperties.getRegionId())
                    .signName(aliYunSmsConfProperties.getSignName())
                    .templateCodes(aliYunSmsConfProperties.getTemplateCodes())
                    .build();
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            argumentValues.addIndexedArgumentValue(0, new DefaultAcsClient(profile));
            argumentValues.addIndexedArgumentValue(1, aliYunSmsConfig);
            registerBean(beanFactory, argumentValues, AliYunSms.class.getName());
        } else {
            instancesMap.forEach((k, v) -> {
                DefaultProfile profile = DefaultProfile.getProfile(v.getRegionId(), v.getAccessKeyId(), v.getAccessKeySecret());
                AliYunSmsConfig aliYunSmsConfig = AliYunSmsConfig.builder()
                        .regionId(v.getRegionId())
                        .signName(v.getSignName())
                        .templateCodes(v.getTemplateCodes())
                        .build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
                argumentValues.addIndexedArgumentValue(0, new DefaultAcsClient(profile));
                argumentValues.addIndexedArgumentValue(1, aliYunSmsConfig);
                registerBean(beanFactory, argumentValues, baneName);
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.aliYunSmsConfProperties = Binder.get(environment).bind("aliyun.sms", AliYunSmsConfProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AliYunSms.class);
        beanDefinition.setBeanClassName(AliYunSms.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
