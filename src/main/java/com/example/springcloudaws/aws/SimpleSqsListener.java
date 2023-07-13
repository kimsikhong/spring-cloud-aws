package com.example.springcloudaws.aws;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleSqsListener {

    @SqsListener("topic-arn")
    public void handle(String message) {
        log.info("topic-arn listen message : {}", message);
    }
}
