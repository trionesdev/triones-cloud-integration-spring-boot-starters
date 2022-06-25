package com.moensun.spring.boot.cloud.integration.tencentcloud.sms;

import com.moensun.cloud.integration.tencentcloud.sms.TencentCloudSms;
import com.moensun.cloud.integration.tencentcloud.sms.TencentCloudSmsConfig;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "tencentcloud.sms", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudSmsConfProperties.class
})
public class TencentCloudSmsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private TencentCloudSmsConfProperties tencentCloudSmsConfProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext(beanFactory);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);

        Map<String, TencentCloudSmsInstanceProperties> instance = tencentCloudSmsConfProperties.getInstance();
        if (MapUtils.isEmpty(instance)) {
            Credential credential = new Credential(tencentCloudSmsConfProperties.getSecretId(), tencentCloudSmsConfProperties.getSecretKey());
            SmsClient smsClient = new SmsClient(credential, "ap-guangzhou", clientProfile);
            TencentCloudSmsConfig tencentCloudSmsConfig = TencentCloudSmsConfig.builder()
                    .sdkAppId(tencentCloudSmsConfProperties.getSdkAppId())
                    .signName(tencentCloudSmsConfProperties.getSignName())
                    .templateCodes(tencentCloudSmsConfProperties.getTemplateCodes())
                    .build();
            genericApplicationContext.registerBean(TencentCloudSms.class,()->new TencentCloudSms(tencentCloudSmsConfig,smsClient));
        } else {
            instance.forEach((k, v) -> {
                Credential credential = new Credential(v.getSecretId(), v.getSecretKey());
                SmsClient smsClient = new SmsClient(credential, "ap-guangzhou", clientProfile);
                TencentCloudSmsConfig tencentCloudSmsConfig = TencentCloudSmsConfig.builder()
                        .sdkAppId(v.getSdkAppId())
                        .signName(v.getSignName())
                        .templateCodes(v.getTemplateCodes())
                        .build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                genericApplicationContext.registerBean(baneName,TencentCloudSms.class,()->new TencentCloudSms(tencentCloudSmsConfig,smsClient));
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.tencentCloudSmsConfProperties = Binder.get(environment).bind("tencentcloud.sms", TencentCloudSmsConfProperties.class).get();
    }

}
