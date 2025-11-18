package com.raviraj.cab_book_driver.service;

import com.raviraj.cab_book_driver.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CabLocationService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void updateLocation(String location){
        kafkaTemplate.send(AppConstant.CAB_LOCATION, location);
    }
}
