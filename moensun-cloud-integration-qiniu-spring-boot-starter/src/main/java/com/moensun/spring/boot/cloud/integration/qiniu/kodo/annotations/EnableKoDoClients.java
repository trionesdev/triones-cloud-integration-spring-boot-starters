package com.moensun.spring.boot.cloud.integration.qiniu.kodo.annotations;

import com.moensun.spring.boot.cloud.integration.qiniu.kodo.KoDoClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {KoDoClientRegister.class})
public @interface EnableKoDoClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
