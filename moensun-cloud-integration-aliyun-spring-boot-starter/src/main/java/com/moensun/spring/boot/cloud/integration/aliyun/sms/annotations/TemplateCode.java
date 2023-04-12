package com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TemplateCode {
    String key() default "";

    String template() default "";
}
