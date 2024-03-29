package com.trionesdev.csi.tencentcloud.sms.autoconfigure;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.trionesdev.csi.tencentcloud.sms.TencentCloudSms;
import com.trionesdev.csi.tencentcloud.sms.TencentCloudSmsConfig;
import lombok.RequiredArgsConstructor;
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

import static com.trionesdev.csi.tencentcloud.sms.autoconfigure.TencentCloudSmsProperties.PREFIX;


@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudSmsProperties.class
})
public class TencentCloudSmsAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private TencentCloudSmsProperties tencentCloudSmsProperties;

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

        Credential credential = new Credential(tencentCloudSmsProperties.getSecretId(), tencentCloudSmsProperties.getSecretKey());
        SmsClient smsClient = new SmsClient(credential, "ap-guangzhou", clientProfile);
        TencentCloudSmsConfig tencentCloudSmsConfig = TencentCloudSmsConfig.builder()
                .sdkAppId(tencentCloudSmsProperties.getSdkAppId())
                .signName(tencentCloudSmsProperties.getSignName())
                .templateCodes(tencentCloudSmsProperties.getTemplateCodes())
                .build();
        genericApplicationContext.registerBean(TencentCloudSms.class, () -> new TencentCloudSms(tencentCloudSmsConfig, smsClient));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.tencentCloudSmsProperties = Binder.get(environment).bind(PREFIX, TencentCloudSmsProperties.class).get();
    }

}
