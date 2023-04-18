package com.moensun.cloud.integration.spring.boot.tencentcloud.sms.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TencentCloudSMSClient {
    String secretId() default "";

    String secretKey() default "";

    String sdkAppId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
