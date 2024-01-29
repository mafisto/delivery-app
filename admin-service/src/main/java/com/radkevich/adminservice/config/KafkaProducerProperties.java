package com.radkevich.adminservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KafkaProducerProperties {
    @Value("${kafka.topics.order-change-status}")
    private String orderChangeStatus;
    @Value("${kafka.topics.order-assign-courier}")
    private String orderAssignCourier;
    @Value("${kafka.topics.order-create-courier}")
    private String createCourier;

}
