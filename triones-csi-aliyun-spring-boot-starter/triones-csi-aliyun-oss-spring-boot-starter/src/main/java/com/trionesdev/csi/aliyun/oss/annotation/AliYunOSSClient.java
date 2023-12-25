package com.trionesdev.csi.aliyun.oss.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AliYunOSSClient {
    String accessKeyId() default "";

    String accessKeySecret() default "";

    String endpoint() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
