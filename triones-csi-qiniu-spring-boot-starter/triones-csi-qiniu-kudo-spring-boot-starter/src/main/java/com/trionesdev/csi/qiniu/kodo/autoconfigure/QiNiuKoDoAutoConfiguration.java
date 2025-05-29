package com.trionesdev.csi.qiniu.kodo.autoconfigure;

import com.trionesdev.csi.qiniu.kodo.QiNiuKoDo;
import com.trionesdev.csi.qiniu.kodo.QiNiuKoDoConfig;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

import static com.trionesdev.csi.qiniu.kodo.autoconfigure.QiNiuKoDoProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {QiNiuKoDoProperties.class})
public class QiNiuKoDoAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private QiNiuKoDoProperties qiNiuKoDoProperties;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        QiNiuKoDoConfig qiNiuKoDoConfig = QiNiuKoDoConfig.builder()
                .accessKey(qiNiuKoDoProperties.getAccessKey())
                .secretKey(qiNiuKoDoProperties.getSecretKey())
                .bucket(qiNiuKoDoProperties.getBucket())
                .urlPrefix(qiNiuKoDoProperties.getUrlPrefix())
                .build();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, qiNiuKoDoConfig);
        registerBean(beanFactory, argumentValues, QiNiuKoDo.class.getName());
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.qiNiuKoDoProperties = Binder.get(environment).bind(PREFIX, QiNiuKoDoProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(QiNiuKoDo.class);
        beanDefinition.setBeanClassName(QiNiuKoDo.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
