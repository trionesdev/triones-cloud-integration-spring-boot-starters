package com.moensun.spring.boot.cloud.integration.huaweicloud.obs;

import com.moensun.cloud.integration.huaweicloud.obs.HuaweiCloudObs;
import com.moensun.cloud.integration.huaweicloud.obs.HuaweiCloudObsConfig;
import com.obs.services.ObsClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class OBSClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String accessKeyId;
    private String secretAccessKey;
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

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
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
        try (ObsClient obsClient = new ObsClient(this.accessKeyId, this.secretAccessKey, this.endpoint)) {
            HuaweiCloudObsConfig huaweiCloudObsConfig = HuaweiCloudObsConfig.builder().bucket(this.bucket).urlPrefix(this.urlPrefix).build();
            HuaweiCloudObs obs = new HuaweiCloudObs(obsClient, huaweiCloudObsConfig);
            return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(obs, args);
                }
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
