package com.trionesdev.csi.huaweicloud.sms.annotation;

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

    String sender() default "";

    TemplateCode[] templateCodes() default {};
}
