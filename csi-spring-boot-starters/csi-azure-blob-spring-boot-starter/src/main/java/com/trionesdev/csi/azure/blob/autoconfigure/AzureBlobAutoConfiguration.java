package com.trionesdev.csi.azure.blob.autoconfigure;


import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.trionesdev.csi.azure.blob.AzureBlob;
import com.trionesdev.csi.azure.blob.AzureBlobConfig;
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

import static com.trionesdev.csi.azure.blob.autoconfigure.AzureBlobProperties.PREFIX;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = PREFIX, value = {"enabled"}, havingValue = "true")
@EnableConfigurationProperties(value = {
        AzureBlobProperties.class
})
public class AzureBlobAutoConfiguration implements EnvironmentAware, BeanFactoryPostProcessor {
    private AzureBlobProperties blobProperties;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(blobProperties.getConnectionString())
                .buildClient();

        // 获取 BlobContainerClient 对象
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(blobProperties.getContainerName());

        AzureBlobConfig blobConfig = AzureBlobConfig.builder()
                .urlPrefix(blobProperties.getUrlPrefix())
                .build();

        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, containerClient);
        argumentValues.addIndexedArgumentValue(1, blobConfig);
        registerBean(beanFactory, argumentValues, AzureBlob.class.getName());

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.blobProperties = Binder.get(environment).bind(PREFIX, AzureBlobProperties.class).get();
    }

    private void registerBean(DefaultListableBeanFactory beanFactory, ConstructorArgumentValues argumentValues, String beanName) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(AzureBlob.class);
        beanDefinition.setBeanClassName(AzureBlob.class.getName());
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}
