package com.moensun.cloud.integration.starter.other.minio.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MinioClient {
    String accessKey() default "";

    String secretKey() default "";

    String endpoint() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
