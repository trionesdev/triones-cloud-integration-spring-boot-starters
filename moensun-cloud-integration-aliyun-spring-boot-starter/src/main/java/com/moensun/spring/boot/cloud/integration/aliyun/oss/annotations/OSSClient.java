package com.moensun.spring.boot.cloud.integration.aliyun.oss.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OSSClient {
    String accessKeyId() default "";

    String accessKeySecret() default "";

    String endpoint() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
