package com.trionesdev.csi.tencentcloud.ses.annotation;


import com.trionesdev.csi.tencentcloud.ses.TencentCloudSes;
import com.trionesdev.csi.tencentcloud.ses.TencentCloudSesConfig;
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
import java.util.Map;

public class TencentCloudSesClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String secretId;
    private String secretKey;
    private String endpoint;
    private String region;
    private String fromAddress;
    private String replyAddress;
    private Map<String, String> templateCodes;
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
    public void afterPropertiesSet() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setReplyAddress(String replyAddress) {
        this.replyAddress = replyAddress;
    }

    public void setTemplateCodes(Map<String, String> templateCodes) {
        this.templateCodes = templateCodes;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    protected <T> T getTarget() {
        try {
            TencentCloudSesConfig sesConfig = TencentCloudSesConfig.builder()
                    .secretId(this.secretId)
                    .secretKey(this.secretKey)
                    .endpoint(this.endpoint)
                    .region(this.region)
                    .fromAddress(this.fromAddress)
                    .replyAddress(this.replyAddress)
                    .templateCodes(this.templateCodes)
                    .build();
            TencentCloudSes ses = new TencentCloudSes(sesConfig);

            return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(ses, args);
                }
            }));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
