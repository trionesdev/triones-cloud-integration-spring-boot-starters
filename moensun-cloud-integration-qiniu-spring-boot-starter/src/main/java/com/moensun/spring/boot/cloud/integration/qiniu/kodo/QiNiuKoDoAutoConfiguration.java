package com.moensun.spring.boot.cloud.integration.qiniu.kodo;

import com.moensun.cloud.integration.qiniu.kodo.QiNiuKoDo;
import com.moensun.cloud.integration.qiniu.kodo.QiNiuKoDoConfig;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
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
@ConditionalOnProperty(prefix = "qiniu.kodo", value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {QiNiuKoDoConfProperties.class})
public class QiNiuKoDoAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {

    private QiNiuKoDoConfProperties qiNiuKoDoConfProperties;

    @Override
    public void postProcessBeanFactory( ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        Auth auth = beanFactory.getBean(Auth.class);
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        Map<String, QiNiuKoDoInstantProperties> instance = qiNiuKoDoConfProperties.getInstance();
        if (MapUtils.isEmpty(instance)) {
            QiNiuKoDoConfig qiNiuKoDoConfig = QiNiuKoDoConfig.builder()
                    .bucket(qiNiuKoDoConfProperties.getBucket())
                    .urlPrefix(qiNiuKoDoConfProperties.getUrlPrefix())
                    .build();
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            argumentValues.addIndexedArgumentValue(0, auth);
            argumentValues.addIndexedArgumentValue(1, uploadManager);
            argumentValues.addIndexedArgumentValue(2, qiNiuKoDoConfig);
            registerBean(beanFactory, argumentValues, QiNiuKoDo.class.getName());
        } else {
            instance.forEach((k, v) -> {
                QiNiuKoDoConfig qiNiuKoDoConfig = QiNiuKoDoConfig.builder()
                        .bucket(v.getBucket())
                        .urlPrefix(v.getUrlPrefix())
                        .build();
                String baneName = StringUtils.isBlank(v.getName()) ? k : v.getName();
                ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
                argumentValues.addIndexedArgumentValue(0, auth);
                argumentValues.addIndexedArgumentValue(1, uploadManager);
                argumentValues.addIndexedArgumentValue(2, qiNiuKoDoConfig);
                registerBean(beanFactory, argumentValues, baneName);
            });
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.qiNiuKoDoConfProperties = Binder.get(environment).bind("qiniu.kodo", QiNiuKoDoConfProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(QiNiuKoDo.class);
        beanDefinition.setBeanClassName(QiNiuKoDo.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
