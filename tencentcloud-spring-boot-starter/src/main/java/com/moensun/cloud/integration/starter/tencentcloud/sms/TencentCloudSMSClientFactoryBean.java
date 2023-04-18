package com.moensun.cloud.integration.starter.tencentcloud.sms;


import com.moensun.cloud.integration.tencentcloud.sms.TencentCloudSms;
import com.moensun.cloud.integration.tencentcloud.sms.TencentCloudSmsConfig;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
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

public class TencentCloudSMSClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware, BeanFactoryAware {
    private String secretId;
    private String secretKey;
    private String sdkAppId;
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


    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setSdkAppId(String sdkAppId) {
        this.sdkAppId = sdkAppId;
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
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);

            Credential credential = new Credential(this.secretId, this.secretKey);
            SmsClient smsClient = new SmsClient(credential, "ap-guangzhou", clientProfile);
            TencentCloudSmsConfig tencentCloudSmsConfig = TencentCloudSmsConfig.builder()
                    .sdkAppId(this.sdkAppId)
                    .signName(this.signName)
                    .templateCodes(this.templateCodes)
                    .build();
            TencentCloudSms sms = new TencentCloudSms(tencentCloudSmsConfig, smsClient);
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
