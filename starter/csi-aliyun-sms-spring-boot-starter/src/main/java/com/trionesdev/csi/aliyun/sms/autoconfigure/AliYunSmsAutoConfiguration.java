package com.trionesdev.csi.aliyun.sms.autoconfigure;

import com.trionesdev.csi.aliyun.sms.AliYunSms;
import com.trionesdev.csi.aliyun.sms.AliYunSmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.trionesdev.csi.aliyun.sms.autoconfigure.AliYunSmsProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {AliYunSmsProperties.class})
public class AliYunSmsAutoConfiguration   {
    private final AliYunSmsProperties aliYunSmsProperties;

//    @SneakyThrows
//    @Override
//    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
//        AliYunSmsConfig aliYunSmsConfig = AliYunSmsConfig.builder()
//                .accessKeyId(aliYunSmsProperties.getAccessKeyId())
//                .accessKeySecret(aliYunSmsProperties.getAccessKeySecret())
//                .regionId(aliYunSmsProperties.getRegionId())
//                .signName(aliYunSmsProperties.getSignName())
//                .templateCodes(aliYunSmsProperties.getTemplateCodes())
//                .build();
//        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
//        argumentValues.addIndexedArgumentValue(0, aliYunSmsConfig);
//        registerBean(beanFactory, argumentValues, AliYunSms.class.getName());
//
//    }
//
//    @Override
//    public void setEnvironment(@NotNull Environment environment) {
//        this.aliYunSmsProperties = Binder.get(environment).bind(PREFIX, AliYunSmsProperties.class).get();
//    }
//
//    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
//        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//        beanDefinition.setBeanClass(AliYunSms.class);
//        beanDefinition.setBeanClassName(AliYunSms.class.getName());
//        beanDefinition.setConstructorArgumentValues(argumentValues);
//        beanFactory.registerBeanDefinition(beanName, beanDefinition);
//    }

    @Bean
    public AliYunSms aliYunSms() throws Exception {
        AliYunSmsConfig aliYunSmsConfig = AliYunSmsConfig.builder()
                .accessKeyId(aliYunSmsProperties.getAccessKeyId())
                .accessKeySecret(aliYunSmsProperties.getAccessKeySecret())
                .regionId(aliYunSmsProperties.getRegionId())
                .signName(aliYunSmsProperties.getSignName())
                .templateCodes(aliYunSmsProperties.getTemplateCodes())
                .build();
        return new AliYunSms(aliYunSmsConfig);
    }

}
