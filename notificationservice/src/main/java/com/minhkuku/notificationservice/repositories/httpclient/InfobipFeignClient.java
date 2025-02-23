package com.minhkuku.notificationservice.repositories.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.minhkuku.notificationservice.configurations.InfobipFeignConfig;
import com.minhkuku.notificationservice.dtos.request.InfobipSmsRequest;
import com.minhkuku.notificationservice.dtos.response.InfobipSmsResponse;

@FeignClient(name = "infobipSmsClient", url = "${infobip.base-url}", configuration = InfobipFeignConfig.class)
public interface InfobipFeignClient {

    @PostMapping("/sms/2/text/advanced")
    InfobipSmsResponse sendSms(@RequestBody InfobipSmsRequest request);
}