package com.trionesdev.csi.tencentcloud.cos.annotation;

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
public @interface TencentCloudCOSClient {
    String accessKey() default "";

    String secretKey() default "";

    String region() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
