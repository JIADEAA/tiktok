package com.jiade.utils;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import javax.net.ssl.KeyManager;
//import javax.net.ssl.X509TrustManager;
import java.util.concurrent.CompletableFuture;

/**
 * @author: JIADE
 * @description: SendSms
 * @date: 2023/4/6 15:35
 **/
@Component
@Slf4j
public class SendSMS {
    @Autowired
    private AliyunProperties aliyunProperties;

    public void sendSMS(String phone, String code) throws Exception {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunProperties.getSecretId(), aliyunProperties.getSecretKey());
        /** use STS Token
         DefaultProfile profile = DefaultProfile.getProfile(
         "<your-region-id>",           // The region ID
         "<your-access-key-id>",       // The AccessKey ID of the RAM account
         "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
         "<your-sts-token>");          // STS Token
         **/

        IAcsClient client = new DefaultAcsClient(profile);


        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("仿tiktok");
        request.setTemplateCode("SMS_276065009");
        log.info(phone);
        if (phone.equals("15727558380")) {
            request.setPhoneNumbers("15727558380");
        } else if (phone.equals("18704646377")){
            request.setPhoneNumbers("18704646377");
        }else if (phone.equals("18370950053")){
            request.setPhoneNumbers("18370950053");
        }else if (phone.equals("15089921298")){
            request.setPhoneNumbers("15089921298");
        }
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }


}

