package com.example.springcloudaws.aws;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private final SnsTemplate snsTemplate;

    NotificationService(SnsTemplate snsTemplate) {
        this.snsTemplate = snsTemplate;
    }

    void sendNotification() {
        // sends String payload
        snsTemplate.sendNotification("topic-arn", "payload", "subject");
        // sends object serialized to JSON
        snsTemplate.sendNotification("topic-arn", new Person("John", "Doe"), "subject");
        // sends a Spring Messaging Message
        Message<String> message = MessageBuilder.withPayload("payload")
                .setHeader("header-name", "header-value")
                .build();
        snsTemplate.send("topic-arn", message);
    }
}
