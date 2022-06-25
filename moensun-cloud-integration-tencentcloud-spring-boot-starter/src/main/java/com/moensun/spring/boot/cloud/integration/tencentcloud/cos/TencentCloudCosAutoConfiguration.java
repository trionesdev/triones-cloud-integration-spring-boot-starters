package com.moensun.spring.boot.cloud.integration.tencentcloud.cos;

import com.moensun.cloud.integration.tencentcloud.cos.TencentCloudCos;
import com.moensun.cloud.integration.tencentcloud.cos.TencentCloudCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
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
@ConditionalOnProperty(prefix = "tencentcloud.cos", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudCosConfProperties.class
})
public class TencentCloudCosAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private TencentCloudCosConfProperties tencentCloudCosConfProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        Map<String, TencentCloudCosInstanceProperties> instance = tencentCloudCosConfProperties.getInstance();
        if (MapUtils.isEmpty(instance)) {
            COSCredentials cred = new BasicCOSCredentials(tencentCloudCosConfProperties.getSecretId(), tencentCloudCosConfProperties.getSecretKey());
            ClientConfig clientConfig = new ClientConfig(new Region(tencentCloudCosConfProperties.getRegion()));
            COSClient cosClient = new COSClient(cred, clientConfig);
            TencentCloudCosConfig tencentCloudCosConfig = TencentCloudCosConfig.builder()
                    .bucket(tencentCloudCosConfProperties.getBucket())
                    .urlPrefix(tencentCloudCosConfProperties.getUrlPrefix())
                    .build();
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            argumentValues.addIndexedArgumentValue(0, cosClient);
            argumentValues.addIndexedArgumentValue(1, tencentCloudCosConfig);
            registerBean(beanFactory, argumentValues, TencentCloudCos.class.getName());
        } else {
            instance.forEach((k, v) -> {
                COSCredentials cred = new BasicCOSCredentials(v.getSecretId(), v.getSecretKey());
                ClientConfig clientConfig = new ClientConfig(new Region(v.getRegion()));
                COSClient cosClient = new COSClient(cred, clientConfig);
                TencentCloudCosConfig tencentCloudCosConfig = TencentCloudCosConfig.builder()
                        .bucket(v.getBucket())
                        .urlPrefix(v.getUrlPrefix())
                        .build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
                argumentValues.addIndexedArgumentValue(0, cosClient);
                argumentValues.addIndexedArgumentValue(1, tencentCloudCosConfig);
                registerBean(beanFactory, argumentValues, baneName);
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.tencentCloudCosConfProperties = Binder.get(environment).bind("tencentcloud.cos", TencentCloudCosConfProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(TencentCloudCos.class);
        beanDefinition.setBeanClassName(TencentCloudCos.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
