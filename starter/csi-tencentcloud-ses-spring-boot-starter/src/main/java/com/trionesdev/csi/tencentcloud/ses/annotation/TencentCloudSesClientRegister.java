package com.trionesdev.csi.tencentcloud.ses.annotation;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TencentCloudSesClientRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
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
        Map<String, Object> attrs = metadata.getAnnotationAttributes(EnableTencentCloudSesClients.class.getName());
        final Class<?>[] channels = attrs == null ? null : (Class<?>[]) attrs.get("clients");
        if (channels == null || channels.length == 0) {
            ClassPathScanningCandidateComponentProvider scanner = getScanner();
            scanner.setResourceLoader(this.resourceLoader);
            scanner.addIncludeFilter(new AnnotationTypeFilter(TencentCloudSesClient.class));
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
                        .getAnnotationAttributes(TencentCloudSesClient.class.getCanonicalName());
                registerTencentCloudSesClient(registry, annotationMetadata, attributes);
            }
        }
    }

    private void registerTencentCloudSesClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata,
                                         Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        ConfigurableBeanFactory beanFactory = registry instanceof ConfigurableBeanFactory
                ? (ConfigurableBeanFactory) registry : null;
        Class clazz = ClassUtils.resolveClassName(className, null);
        TencentCloudSesClientFactoryBean factoryBean = new TencentCloudSesClientFactoryBean();
        factoryBean.setBeanFactory(beanFactory);
        factoryBean.setType(clazz);
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(clazz, () -> {
            factoryBean.setSecretId(getSecretId(beanFactory, attributes));
            factoryBean.setSecretKey(getSecretKey(beanFactory, attributes));
            factoryBean.setEndpoint(getEndpoint(beanFactory, attributes));
            factoryBean.setRegion(getRegion(beanFactory, attributes));
            factoryBean.setFromAddress(getFromAddress(beanFactory, attributes));
            factoryBean.setReplyAddress(getReplyAddress(beanFactory, attributes));
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
                .getAnnotationAttributes(EnableTencentCloudSesClients.class.getCanonicalName());

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


    String getSecretId(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("secretId");
        return resolve(beanFactory, endpoint);
    }

    String getSecretKey(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("secretKey");
        return resolve(beanFactory, endpoint);
    }

    String getEndpoint(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("endpoint");
        return resolve(beanFactory, endpoint);
    }

    String getRegion(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("region");
        return resolve(beanFactory, endpoint);
    }

    String getFromAddress(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("fromAddress");
        return resolve(beanFactory, endpoint);
    }

    String getReplyAddress(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String endpoint = (String) attributes.get("replyAddress");
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
