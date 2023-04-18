package com.moensun.tencentcloud.cos.autoconfigure;

import com.moensun.tencentcloud.cos.TencentCloudCOSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {TencentCloudCOSClientRegister.class})
public @interface EnableTencentCloudCOSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}