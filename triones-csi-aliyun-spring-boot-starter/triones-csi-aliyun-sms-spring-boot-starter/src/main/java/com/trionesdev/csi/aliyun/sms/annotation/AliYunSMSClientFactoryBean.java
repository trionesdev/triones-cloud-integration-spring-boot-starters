package com.trionesdev.csi.aliyun.sms.annotation;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.trionesdev.csi.aliyun.sms.AliYunSms;
import com.trionesdev.csi.aliyun.sms.AliYunSmsConfig;
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

public class AliYunSMSClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
    private String signName;
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

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public void setTemplateCodes(Map<String, String> templateCodes) {
        this.templateCodes = templateCodes;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    protected <T> T getTarget() {
        try {
            Config config = new Config().setAccessKeyId(this.accessKeyId).setAccessKeySecret(this.accessKeySecret);
            AliYunSmsConfig aliYunSmsConfig = AliYunSmsConfig.builder()
                    .regionId(this.regionId)
                    .signName(this.signName)
                    .templateCodes(this.templateCodes)
                    .build();
            AliYunSms sms = new AliYunSms(new Client(config), aliYunSmsConfig);
            return (T) this.type.cast(Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    return method.invoke(sms, args);
                }
            }));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
