package com.example.common.utils;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.example.common.properties.AliyunConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SmsUtil {

    // 产品域名开发者无需替换
    private static final String ENDPOINT = "dysmsapi.aliyuncs.com";

    private final AliyunConfig aliyunConfig;


    /**
     * 发送短信
     *
     * @param phoneNumber 手机号码
     * @param signName 签名名称
     * @param templateCode 模板编码
     * @param templateParam 模板参数
     * @return 发送结果
     * @throws Exception 可能抛出的异常
     */
    public boolean sendSms(String phoneNumber, String signName, String templateCode, String templateParam) throws Exception {

        // 参数校验
        if (Objects.isNull(phoneNumber) || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (Objects.isNull(signName) || signName.isEmpty()) {
            throw new IllegalArgumentException("Sign name cannot be null or empty.");
        }
        if (Objects.isNull(templateCode) || templateCode.isEmpty()) {
            throw new IllegalArgumentException("Template code cannot be null or empty.");
        }

        // 初始化客户端
        Config config = new Config()
                .setAccessKeyId(aliyunConfig.getAccessKeyId())
                .setAccessKeySecret(aliyunConfig.getAccessKeySecret());
        config.endpoint = ENDPOINT;

        // 实例化客户端
        Client client = new Client(config);

        // 发送短信
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setTemplateParam(templateParam);

        try {
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            System.out.println(sendSmsResponse.getBody().getMessage());
            return true;
        } catch (TeaException error) {
            System.err.println("Error Message: " + error.getMessage());
            System.err.println("Error Data: " + error.getData().get("Recommend"));
            throw error;
        } catch (Exception e) {
            TeaException error = new TeaException(e.getMessage(), e);
            System.err.println("Error Message: " + error.getMessage());
            System.err.println("Error Data: " + error.getData().get("Recommend"));
            throw error;
        }
    }
}