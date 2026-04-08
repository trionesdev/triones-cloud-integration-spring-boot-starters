package com.trionesdev.csi.huaweicloud.sms.autoconfigure;

import com.trionesdev.csi.huaweicloud.sms.HuaweiCloudSmsCredentials;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.trionesdev.csi.huaweicloud.sms.autoconfigure.HuaweiCloudSmsProperties.PREFIX;

@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = PREFIX)
public class HuaweiCloudSmsProperties extends HuaweiCloudSmsCredentials {
    public static final String PREFIX = "triones.huaweicloud.sms";
    private Boolean enabled;
    private String sender;
    private Map<String, String> templateCodes;

}
