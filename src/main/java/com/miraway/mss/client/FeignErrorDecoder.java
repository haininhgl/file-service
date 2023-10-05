package com.miraway.mss.client;

import com.miraway.mss.utils.APIUtils;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Error when using Feign client to send HTTP Request. Status code {}, methodKey {}", response.status(), methodKey);
        return APIUtils.handleHttpFailure(response.status());
    }
}
