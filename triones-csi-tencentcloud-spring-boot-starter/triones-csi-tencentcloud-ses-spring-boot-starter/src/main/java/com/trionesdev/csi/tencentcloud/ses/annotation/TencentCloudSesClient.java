package com.trionesdev.csi.tencentcloud.ses.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TencentCloudSesClient {
    String secretId() default "";

    String secretKey() default "";

    String endpoint() default "";

    String region() default "";

    String fromAddress() default "";

    String replyAddress() default "";

    TemplateCode[] templateCodes() default {};
}
