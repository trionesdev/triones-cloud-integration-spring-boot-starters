package com.moensun.aliyun.oss.annotation;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.moensun.aliyun.oss.AliYunOSS;
import com.moensun.aliyun.oss.AliYunOssConfig;
import com.moensun.cloud.integration.aliyun.oss.AliYunOSS;
import com.moensun.cloud.integration.aliyun.oss.AliYunOssConfig;
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

public class AliYunOSSClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
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

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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
        OSS oss = new OSSClientBuilder().build(this.endpoint, this.accessKeyId, this.accessKeySecret);
        AliYunOssConfig aliYunOssConfig = AliYunOssConfig.builder()
                .bucket(this.bucket)
                .urlPrefix(this.urlPrefix)
                .build();
        AliYunOSS aliYunOSS = new AliYunOSS(oss, aliYunOssConfig);
        return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(aliYunOSS, args);
            }
        }));
    }
}
