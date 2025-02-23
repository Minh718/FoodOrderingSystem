package com.minhkuku.notificationservice.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.minhkuku.event.dto.NotificationEvent;
import com.minhkuku.notificationservice.services.SmsService;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    SmsService smsService;

    @Value("${infobip.from}")
    @NonFinal
    String from;

    @KafkaListener(topics = "notify-sms-register")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("Message received: {}", message);
        smsService.sendSms(from, message.getRecipient(), message.getBody());
    }
}