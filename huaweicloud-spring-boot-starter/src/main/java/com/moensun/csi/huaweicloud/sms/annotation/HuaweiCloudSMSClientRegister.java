package com.moensun.csi.huaweicloud.sms.annotation;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

public class HuaweiCloudSMSClientRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerSmsClients(metadata, registry);
    }

    private void registerSmsClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableHuaweiCloudSMSClients.class.getName());
        final Class<?>[] channels = attrs == null ? null : (Class<?>[]) attrs.get("clients");
        if (channels == null || channels.length == 0) {
            ClassPathScanningCandidateComponentProvider scanner = getScanner();
            scanner.setResourceLoader(this.resourceLoader);
            scanner.addIncludeFilter(new AnnotationTypeFilter(HuaweiCloudSMSClient.class));
            Set<String> basePackages = getBasePackages(metadata);
            for (String basePackage : basePackages) {
                candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
            }
        } else {
            for (Class<?> clazz : channels) {
                candidateComponents.add(new AnnotatedGenericBeanDefinition(clazz));
            }
        }

        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(HuaweiCloudSMSClient.class.getCanonicalName());
                registerAliYunOssClient(registry, annotationMetadata, attributes);
            }
        }
    }

    private void registerAliYunOssClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
                                         Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        Class clazz = ClassUtils.resolveClassName(className, null);
        HuaweiCloudSMSClientFactoryBean factoryBean = new HuaweiCloudSMSClientFactoryBean();
        factoryBean.setBeanFactory(beanFactory);
        factoryBean.setType(clazz);
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(clazz, () -> {
            factoryBean.setAppKey(getAppKey(beanFactory, attributes));
            factoryBean.setAppSecret(getAppSecret(beanFactory, attributes));
            factoryBean.setRegionId(getRegionId(beanFactory, attributes));
            factoryBean.setSignName(getSignName(beanFactory, attributes));
            factoryBean.setTemplateCodes(getTemplateCodes(beanFactory, attributes));
            return factoryBean.getObject();
        });
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.setLazyInit(true);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, null);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableHuaweiCloudSMSClients.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

    private String resolve(ConfigurableBeanFactory beanFactory, String value) {
        if (StringUtils.hasText(value)) {
            if (beanFactory == null) {
                return this.environment.resolvePlaceholders(value);
            }
            BeanExpressionResolver resolver = beanFactory.getBeanExpressionResolver();
            String resolved = beanFactory.resolveEmbeddedValue(value);
            if (resolver == null) {
                return resolved;
            }
            Object evaluateValue = resolver.evaluate(resolved, new BeanExpressionContext(beanFactory, null));
            if (evaluateValue != null) {
                return String.valueOf(evaluateValue);
            }
            return null;
        }
        return value;
    }


    String getAppKey(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("appKey");
        return resolve(beanFactory, endpoint);
    }

    String getAppSecret(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("appSecret");
        return resolve(beanFactory, endpoint);
    }

    String getRegionId(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("regionId");
        return resolve(beanFactory, endpoint);
    }

    String getSignName(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("signName");
        return resolve(beanFactory, endpoint);
    }

    Map<String, String> getTemplateCodes(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        Map<String, String> templateCodeMap = new HashMap<>();
        for (AnnotationAttributes templateCodes : (AnnotationAttributes[]) attributes.get("templateCodes")) {
            String key = (String) templateCodes.get("key");
            String template = (String) templateCodes.get("template");
            templateCodeMap.put(resolve(beanFactory, key), resolve(beanFactory, template));
        }
        return templateCodeMap;
    }

}
