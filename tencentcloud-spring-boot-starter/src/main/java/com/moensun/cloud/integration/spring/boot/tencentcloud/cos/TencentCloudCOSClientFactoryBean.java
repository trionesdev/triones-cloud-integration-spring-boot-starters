package com.moensun.cloud.integration.spring.boot.tencentcloud.cos;

import com.moensun.cloud.integration.tencentcloud.cos.TencentCloudCos;
import com.moensun.cloud.integration.tencentcloud.cos.TencentCloudCosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
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

public class TencentCloudCOSClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String accessKey;
    private String secretKey;
    private String region;
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

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setRegion(String region) {
        this.region = region;
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
        COSCredentials cred = new BasicCOSCredentials(this.accessKey, this.secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(this.region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        TencentCloudCosConfig tencentCloudCosConfig = TencentCloudCosConfig.builder()
                .bucket(this.bucket)
                .urlPrefix(this.urlPrefix)
                .build();
        TencentCloudCos cos = new TencentCloudCos(cosClient, tencentCloudCosConfig);
        return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(cos, args);
            }
        }));
    }
}
