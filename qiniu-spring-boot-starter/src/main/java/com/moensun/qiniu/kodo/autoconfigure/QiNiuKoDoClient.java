package com.moensun.qiniu.kodo.autoconfigure;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface QiNiuKoDoClient {
    String accessKey() default "";

    String secretKey() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
