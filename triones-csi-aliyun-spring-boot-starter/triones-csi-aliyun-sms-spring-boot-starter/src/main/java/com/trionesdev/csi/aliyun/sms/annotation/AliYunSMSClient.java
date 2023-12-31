package com.trionesdev.csi.aliyun.sms.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AliYunSMSClient {
    String accessKeyId() default "";

    String accessKeySecret() default "";

    String regionId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
