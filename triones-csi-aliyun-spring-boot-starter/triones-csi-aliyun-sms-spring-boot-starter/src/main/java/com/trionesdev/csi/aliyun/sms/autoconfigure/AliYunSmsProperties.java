package com.trionesdev.csi.aliyun.sms.autoconfigure;

import com.trionesdev.csi.aliyun.sms.AliYunSmsCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.trionesdev.csi.aliyun.sms.autoconfigure.AliYunSmsProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class AliYunSmsProperties extends AliYunSmsCredentials {
    public static final String PREFIX = "triones.aliyun.sms";
    private Boolean enabled;
    private Map<String,String> templateCodes;

}
