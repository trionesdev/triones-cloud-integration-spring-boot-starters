package com.trionesdev.csi.tencentcloud.cos.autoconfigure;

import com.trionesdev.csi.tencentcloud.cos.TencentCloudCos;
import com.trionesdev.csi.tencentcloud.cos.TencentCloudCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
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
@ConditionalOnProperty(prefix = "triones.tencentcloud.cos", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        TencentCloudCosProperties.class
})
public class TencentCloudCosAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private TencentCloudCosProperties tencentCloudCosProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        COSCredentials cred = new BasicCOSCredentials(tencentCloudCosProperties.getSecretId(), tencentCloudCosProperties.getSecretKey());
        ClientConfig clientConfig = new ClientConfig(new Region(tencentCloudCosProperties.getRegion()));
        COSClient cosClient = new COSClient(cred, clientConfig);
        TencentCloudCosConfig tencentCloudCosConfig = TencentCloudCosConfig.builder()
                .bucket(tencentCloudCosProperties.getBucket())
                .urlPrefix(tencentCloudCosProperties.getUrlPrefix())
                .build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, cosClient);
        argumentValues.addIndexedArgumentValue(1, tencentCloudCosConfig);
        registerBean(beanFactory, argumentValues, TencentCloudCos.class.getName());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.tencentCloudCosProperties = Binder.get(environment).bind("triones.tencentcloud.cos", TencentCloudCosProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(TencentCloudCos.class);
        beanDefinition.setBeanClassName(TencentCloudCos.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }


}
