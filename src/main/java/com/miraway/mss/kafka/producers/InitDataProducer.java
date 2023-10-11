package com.miraway.mss.kafka.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miraway.mss.kafka.messages.InitDataMessage;
import com.miraway.mss.modules.common.exception.InternalServerException;
import com.miraway.mss.utils.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import static com.miraway.mss.constants.Constants.INIT_DATA_TOPIC;

@Component
public class InitDataProducer {

    private final Logger log = LoggerFactory.getLogger(InitDataProducer.class);

    private final StreamBridge streamBridge;

    public InitDataProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void sendMessage(InitDataMessage message) throws InternalServerException {
        try {
            byte[] messageBytes = DataUtils.convertObjectToJsonBytes(message);

            streamBridge.send(INIT_DATA_TOPIC, messageBytes);
        } catch (JsonProcessingException e) {
            log.error("InitDataProducer -> sendMessage -> exception: {}", e.getMessage());
            throw new InternalServerException();
        }
    }
}
