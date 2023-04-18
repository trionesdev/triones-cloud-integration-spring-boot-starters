package com.moensun.huaweicloud.sms.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {HuaweiCloudSMSClientRegister.class})
public @interface EnableHuaweiCloudSMSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
