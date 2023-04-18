package com.moensun.tencentcloud.cos.annotation;

import java.lang.annotation.*;

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
