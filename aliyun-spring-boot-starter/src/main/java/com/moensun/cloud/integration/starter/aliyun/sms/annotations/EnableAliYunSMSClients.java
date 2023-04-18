package com.moensun.cloud.integration.starter.aliyun.sms.annotations;

import com.moensun.cloud.integration.starter.aliyun.sms.AliYunSMSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {AliYunSMSClientRegister.class})
public @interface EnableAliYunSMSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
