package com.trionesdev.csi.minio.annotation;

import com.trionesdev.csi.minio.Minio;
import com.trionesdev.csi.minio.MinioConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

public class MinioFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String urlPrefix;
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

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    protected <T> T getTarget() {

        MinioConfig minioConfig = MinioConfig.builder()
                .accessKey(this.accessKey)
                .secretKey(this.secretKey)
                .endpoint(this.endpoint)
                .bucket(this.bucket)
                .urlPrefix(this.urlPrefix)
                .build();
        Minio minio = new Minio(  minioConfig);
        return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, (proxy, method, args) -> method.invoke(minio, args)));
    }
}
