package com.moensun.cloud.integration.starter.other.minio.annotations;

import com.moensun.cloud.integration.starter.other.minio.MinioClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {MinioClientRegister.class})
public @interface EnableMinioClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
