package com.moensun.cloud.integration.starter.aliyun.oss.annotations;

import com.moensun.cloud.integration.starter.aliyun.oss.AliYunOSSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {AliYunOSSClientRegister.class})
public @interface EnableAliYunOSSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
