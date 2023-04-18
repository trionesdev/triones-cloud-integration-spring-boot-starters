package com.moensun.tencentcloud.sms.autoconfigure;

import com.moensun.tencentcloud.sms.TencentCloudSMSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {TencentCloudSMSClientRegister.class})
public @interface EnableTencentCloudSMSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
