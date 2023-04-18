package com.moensun.qiniu.kodo.autoconfigure;

import com.moensun.qiniu.kodo.QiNiuKoDoClientRegister;
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
