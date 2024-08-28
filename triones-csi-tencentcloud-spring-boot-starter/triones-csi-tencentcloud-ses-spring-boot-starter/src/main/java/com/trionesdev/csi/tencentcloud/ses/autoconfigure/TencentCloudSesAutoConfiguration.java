package com.trionesdev.csi.tencentcloud.ses.autoconfigure;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.trionesdev.csi.tencentcloud.ses.TencentCloudSes;
import com.trionesdev.csi.tencentcloud.ses.TencentCloudSesConfig;
import lombok.RequiredArgsConstructor;
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

import static com.trionesdev.csi.tencentcloud.ses.autoconfigure.TencentCloudSesProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudSesProperties.class
})
public class TencentCloudSesAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private TencentCloudSesProperties tencentCloudSesProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext(beanFactory);

        Credential cred = new Credential(tencentCloudSesProperties.getSecretId(), tencentCloudSesProperties.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        if (StringUtils.isBlank(tencentCloudSesProperties.getEndpoint())) {
            httpProfile.setEndpoint("ses.tencentcloudapi.com");
        } else {
            httpProfile.setEndpoint(tencentCloudSesProperties.getEndpoint());
        }
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        SesClient client = new SesClient(cred, tencentCloudSesProperties.getRegion(), clientProfile);
        TencentCloudSesConfig sesConfig = TencentCloudSesConfig.builder()
                .endpoint(tencentCloudSesProperties.getEndpoint())
                .region(tencentCloudSesProperties.getRegion())
                .fromAddress(tencentCloudSesProperties.getFromAddress())
                .replyAddress(tencentCloudSesProperties.getReplyAddress())
                .templateCodes(tencentCloudSesProperties.getTemplateCodes())
                .build();
        genericApplicationContext.registerBean(TencentCloudSes.class, () -> new TencentCloudSes(sesConfig, client));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.tencentCloudSesProperties = Binder.get(environment).bind(PREFIX, TencentCloudSesProperties.class).get();
    }
}
