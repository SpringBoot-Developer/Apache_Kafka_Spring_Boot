package com.delivery.boy.service;

import com.delivery.boy.config.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendLocationUpdate(String location) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(AppConstant.LOCATION_TOPIC_NAME, location);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Update location=[{}] with offset=[{}]", location, result.getRecordMetadata().offset());
            } else {
                log.info("Unable to update location=[{}] due to: {}", location, ex.getMessage());
            }
        });
    }

}
