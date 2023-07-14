package com.example.springcloudaws.config;

import com.example.springcloudaws.domain.user.UserEventHandler;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.MessageListenerContainer;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class SqsConfiguration {

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
        SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();
        messageConverter.setPayloadMessageConverter(new MappingJackson2MessageConverter());
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
                        .acknowledgementInterval(Duration.ofSeconds(3))
                        .acknowledgementThreshold(5)
                        .acknowledgementOrdering(AcknowledgementOrdering.ORDERED)
                        .messageConverter(messageConverter))
                .sqsAsyncClient(sqsAsyncClient())
                .build();
    }

    @Bean
    MessageListenerContainer<Object> topicArnContainer(SqsAsyncClient sqsAsyncClient, UserEventHandler userEventHandler) {
        return SqsMessageListenerContainerFactory
                .builder()
                .sqsAsyncClient(sqsAsyncClient)
                .messageListener(userEventHandler::onMessage)
                .build()
                .createContainer("topic-arn");
    }

    @Bean
    SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder().build();
    }

}
