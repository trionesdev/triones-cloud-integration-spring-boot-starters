package com.moensun.spring.boot.cloud.integration.tencentcloud.cos.annotations;

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
