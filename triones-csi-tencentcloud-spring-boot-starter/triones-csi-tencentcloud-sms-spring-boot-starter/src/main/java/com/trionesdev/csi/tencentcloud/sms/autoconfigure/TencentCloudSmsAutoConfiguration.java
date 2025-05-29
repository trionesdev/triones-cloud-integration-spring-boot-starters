package com.trionesdev.csi.tencentcloud.sms.autoconfigure;

import com.trionesdev.csi.tencentcloud.sms.TencentCloudSms;
import com.trionesdev.csi.tencentcloud.sms.TencentCloudSmsConfig;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    private TencentCloudSmsProperties smsProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext(beanFactory);

        TencentCloudSmsConfig tencentCloudSmsConfig = TencentCloudSmsConfig.builder()
                .secretId(smsProperties.getSecretId())
                .secretKey(smsProperties.getSecretKey())
                .sdkAppId(smsProperties.getSdkAppId())
                .signName(smsProperties.getSignName())
                .templateCodes(smsProperties.getTemplateCodes())
                .build();
        genericApplicationContext.registerBean(TencentCloudSms.class, () -> new TencentCloudSms(tencentCloudSmsConfig));
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.smsProperties = Binder.get(environment).bind(PREFIX, TencentCloudSmsProperties.class).get();
    }

}
