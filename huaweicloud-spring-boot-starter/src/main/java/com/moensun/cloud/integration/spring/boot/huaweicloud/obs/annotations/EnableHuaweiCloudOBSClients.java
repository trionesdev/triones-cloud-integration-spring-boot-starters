package com.moensun.cloud.integration.spring.boot.huaweicloud.obs.annotations;

import com.moensun.cloud.integration.spring.boot.huaweicloud.obs.HuaweiCloudOBSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {HuaweiCloudOBSClientRegister.class})
public @interface EnableHuaweiCloudOBSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
