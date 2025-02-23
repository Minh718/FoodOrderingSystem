package com.minhkuku.notificationservice.services;

import org.springframework.stereotype.Service;

import com.minhkuku.notificationservice.dtos.request.InfobipSmsRequest;
import com.minhkuku.notificationservice.dtos.response.InfobipSmsResponse;
import com.minhkuku.notificationservice.repositories.httpclient.InfobipFeignClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final InfobipFeignClient infobipFeignClient;

    public InfobipSmsResponse sendSms(String from, String to, String text) {
        InfobipSmsRequest request = new InfobipSmsRequest(new InfobipSmsRequest.Message[] {
                new InfobipSmsRequest.Message(from,
                        new InfobipSmsRequest.Message.Destination[] { new InfobipSmsRequest.Message.Destination(to) },
                        text)
        });

        return infobipFeignClient.sendSms(request);
    }
}