package com.moensun.spring.boot.cloud.integration.tencentcloud.sms.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SMSClient {
    String secretId() default "";

    String secretKey() default "";

    String sdkAppId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
