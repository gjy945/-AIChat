package com.example.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunConfig {
    private String accessKeyId;
    private String accessKeySecret;
    private SmsConfig sms;

    // 内部类，用于绑定 sms 下的配置
    @Data

    public static class SmsConfig {
        private String signerName;
        private String templateCodePhoneApprove;
        private String templateCodePhoneReject;
        private String templateCodeCode;
    }
}