package com.moensun.aliyun.sms.autoconfigure;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TemplateCode {
    String key() default "";

    String template() default "";
}
