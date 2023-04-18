package com.moensun.cloud.integration.spring.boot.huaweicloud.sms.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HuaweiCloudSMSClient {
    String appKey() default "";

    String appSecret() default "";

    String regionId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
