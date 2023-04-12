package com.moensun.spring.boot.cloud.integration.tencentcloud.sms.annotations;

import com.moensun.spring.boot.cloud.integration.tencentcloud.sms.TencentCloudSMSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {TencentCloudSMSClientRegister.class})
public @interface EnableSMSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
