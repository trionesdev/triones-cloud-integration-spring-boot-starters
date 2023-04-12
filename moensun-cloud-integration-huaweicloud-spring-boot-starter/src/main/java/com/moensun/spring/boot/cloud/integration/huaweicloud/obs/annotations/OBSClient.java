package com.moensun.spring.boot.cloud.integration.huaweicloud.obs.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OBSClient {
    String accessKeyId() default "";

    String accessKeySecret() default "";

    String endpoint() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
