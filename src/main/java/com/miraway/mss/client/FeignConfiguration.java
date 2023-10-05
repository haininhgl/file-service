package com.miraway.mss.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new UserFeignClientInterceptor();
    }

    @Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
