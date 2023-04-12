package com.moensun.spring.boot.cloud.integration.huaweicloud.obs.annotations;

import com.moensun.spring.boot.cloud.integration.huaweicloud.obs.OBSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {OBSClientRegister.class})
public @interface EnableOBSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
