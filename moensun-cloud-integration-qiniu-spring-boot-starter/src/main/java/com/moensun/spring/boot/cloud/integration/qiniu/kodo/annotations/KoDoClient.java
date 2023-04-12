package com.moensun.spring.boot.cloud.integration.qiniu.kodo.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface KoDoClient {
    String accessKey() default "";

    String secretKey() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
