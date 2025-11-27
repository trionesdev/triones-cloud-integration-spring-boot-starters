package com.trionesdev.csi.azure.blob.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AzureBlobClient {
    String accessKeyId() default "";

    String accessKeySecret() default "";

    String endpoint() default "";

    String bucket() default "";

    String urlPrefix() default "";
}
