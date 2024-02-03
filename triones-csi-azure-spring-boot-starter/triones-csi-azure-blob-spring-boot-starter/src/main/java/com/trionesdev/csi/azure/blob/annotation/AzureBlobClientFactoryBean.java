package com.trionesdev.csi.azure.blob.annotation;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.trionesdev.csi.azure.blob.AzureBlob;
import com.trionesdev.csi.azure.blob.AzureBlobConfig;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AzureBlobClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    @Setter
    private String connectionString;
    @Setter
    private String containerName;
    @Setter
    private String urlPrefix;
    @Setter
    private Class<?> type;
    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() {
        return getTarget();
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected <T> T getTarget() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(this.connectionString)
                .buildClient();

        // 获取 BlobContainerClient 对象
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(this.containerName);
        AzureBlobConfig blobConfig = AzureBlobConfig.builder()
                .urlPrefix(urlPrefix)
                .build();
        AzureBlob azureBlob = new AzureBlob(containerClient, blobConfig);
        return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(azureBlob, args);
            }
        }));
    }
}
