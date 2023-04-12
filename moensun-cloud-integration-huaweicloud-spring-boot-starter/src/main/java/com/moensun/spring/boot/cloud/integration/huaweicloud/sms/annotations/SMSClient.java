package com.moensun.spring.boot.cloud.integration.huaweicloud.sms.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SMSClient {
    String appKey() default "";

    String appSecret() default "";

    String regionId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
