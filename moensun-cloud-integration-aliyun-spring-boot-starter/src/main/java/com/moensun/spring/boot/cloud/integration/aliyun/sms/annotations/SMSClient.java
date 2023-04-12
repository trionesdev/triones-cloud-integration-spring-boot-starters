package com.moensun.spring.boot.cloud.integration.aliyun.sms.annotations;

import java.lang.annotation.*;
import java.util.Map;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SMSClient {
    String accessKeyId() default "";

    String accessKeySecret() default "";

    String regionId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
