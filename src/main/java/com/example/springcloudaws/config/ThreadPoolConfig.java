package com.example.springcloudaws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "AsyncTaskExecutor")
    public Executor asyncTaskExecutor() {
        return new ThreadPoolExecutor(20, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
    }

}
