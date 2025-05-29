package com.trionesdev.csi.tencentcloud.ses.autoconfigure;

import com.trionesdev.csi.tencentcloud.ses.TencentCloudSes;
import com.trionesdev.csi.tencentcloud.ses.TencentCloudSesConfig;
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

import static com.trionesdev.csi.tencentcloud.ses.autoconfigure.TencentCloudSesProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudSesProperties.class
})
public class TencentCloudSesAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private TencentCloudSesProperties sesProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext(beanFactory);

        TencentCloudSesConfig sesConfig = TencentCloudSesConfig.builder()
                .secretId(sesProperties.getSecretId())
                .secretKey(sesProperties.getSecretKey())
                .endpoint(sesProperties.getEndpoint())
                .region(sesProperties.getRegion())
                .fromAddress(sesProperties.getFromAddress())
                .replyAddress(sesProperties.getReplyAddress())
                .templateCodes(sesProperties.getTemplateCodes())
                .build();
        genericApplicationContext.registerBean(TencentCloudSes.class, () -> new TencentCloudSes(sesConfig));
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.sesProperties = Binder.get(environment).bind(PREFIX, TencentCloudSesProperties.class).get();
    }
}
