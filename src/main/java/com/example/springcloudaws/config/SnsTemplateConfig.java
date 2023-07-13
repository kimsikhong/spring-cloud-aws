package com.example.springcloudaws.config;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class SnsTemplateConfig {

    @Bean
    SnsTemplate snsTemplate(SnsClient snsClient) {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        return new SnsTemplate(snsClient, messageConverter);
    }

}
