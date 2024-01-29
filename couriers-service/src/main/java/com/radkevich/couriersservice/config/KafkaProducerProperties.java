package com.radkevich.couriersservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KafkaProducerProperties {
    @Value("${kafka.topics.order-change-status}")
    private String orderChangeStatus;
    @Value("${kafka.topics.order-change-location}")
    private String orderChangeLocation;

}
