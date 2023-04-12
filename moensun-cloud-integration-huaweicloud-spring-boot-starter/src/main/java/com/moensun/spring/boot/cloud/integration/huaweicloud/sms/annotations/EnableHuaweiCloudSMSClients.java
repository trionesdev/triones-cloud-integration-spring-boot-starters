package com.moensun.spring.boot.cloud.integration.huaweicloud.sms.annotations;

import com.moensun.spring.boot.cloud.integration.huaweicloud.sms.HuaweiCloudSMSClientRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(value = {HuaweiCloudSMSClientRegister.class})
public @interface EnableHuaweiCloudSMSClients {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    //    Class<?>[] defaultConfiguration() default {};
    Class<?>[] clients() default {};
}
