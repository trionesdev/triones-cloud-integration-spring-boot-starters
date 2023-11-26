package com.moensun.csi.tencentcloud.sms.annotattion;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TencentCloudSMSClient {
    String secretId() default "";

    String secretKey() default "";

    String sdkAppId() default "";

    String signName() default "";

    TemplateCode[] templateCodes() default {};
}
