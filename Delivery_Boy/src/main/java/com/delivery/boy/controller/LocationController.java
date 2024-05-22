package com.delivery.boy.controller;

import com.delivery.boy.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private KafkaProducerService kafkaService;

    @PostMapping("/update")
    public ResponseEntity<?> updateLocation() {

        // Generate random coordinates between -100 and 100
        double latitude = ThreadLocalRandom.current().nextDouble(-100, 100);
        double longitude = ThreadLocalRandom.current().nextDouble(-100, 100);

        String location = String.format("(%f, %f)", latitude, longitude);
        kafkaService.sendLocationUpdate(location);

        return ResponseEntity.ok(Map.of("message", "Location updated"));

    }
}
