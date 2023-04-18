package com.moensun.cloud.integration.starter.qiniu.kodo.annotations;

import com.moensun.cloud.integration.starter.qiniu.kodo.QiNiuKoDoClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {QiNiuKoDoClientRegister.class})
public @interface EnableQiNiuKoDoClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
