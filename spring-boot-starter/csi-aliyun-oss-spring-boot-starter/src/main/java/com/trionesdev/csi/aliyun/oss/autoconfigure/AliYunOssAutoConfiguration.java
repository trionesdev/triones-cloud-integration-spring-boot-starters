package com.trionesdev.csi.aliyun.oss.autoconfigure;

import com.trionesdev.csi.aliyun.oss.AliYunOSS;
import com.trionesdev.csi.aliyun.oss.AliYunOssConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.aliyun.oss.autoconfigure.AliYunOssProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        AliYunOssProperties.class
})
public class AliYunOssAutoConfiguration {
    private final AliYunOssProperties ossProperties;

//    @Override
//    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
//        AliYunOssConfig aliYunOssProperties = AliYunOssConfig.builder()
//                .accessKeyId(ossProperties.getAccessKeyId())
//                .accessKeySecret(ossProperties.getAccessKeySecret())
//                .endpoint(ossProperties.getEndpoint())
//                .bucket(ossProperties.getBucket())
//                .urlPrefix(ossProperties.getUrlPrefix())
//                .build();
//        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
//        argumentValues.addIndexedArgumentValue(0, aliYunOssProperties);
//        registerBean(beanFactory, argumentValues, AliYunOSS.class.getName());
//
//    }
//
//    @Override
//    public void setEnvironment(@NotNull Environment environment) {
//        this.ossProperties = Binder.get(environment).bind(PREFIX, AliYunOssProperties.class).get();
//    }
//
//    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
//        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//        beanDefinition.setBeanClass(AliYunOSS.class);
//        beanDefinition.setBeanClassName(AliYunOSS.class.getName());
//        beanDefinition.setConstructorArgumentValues(argumentValues);
//        beanFactory.registerBeanDefinition(beanName, beanDefinition);
//    }

    @Bean
    public AliYunOSS aliYunOSS() throws Exception {
        AliYunOssConfig aliYunOssConfig = AliYunOssConfig.builder()
                .accessKeyId(ossProperties.getAccessKeyId())
                .accessKeySecret(ossProperties.getAccessKeySecret())
                .endpoint(ossProperties.getEndpoint())
                .bucket(ossProperties.getBucket())
                .urlPrefix(ossProperties.getUrlPrefix())
                .build();
        return new AliYunOSS(aliYunOssConfig);
    }

}
