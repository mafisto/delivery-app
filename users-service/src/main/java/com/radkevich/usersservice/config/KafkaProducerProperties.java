package com.radkevich.usersservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KafkaProducerProperties {
    @Value("${kafka.topics.order-create}")
    private String orderCreateTopic;
    @Value("${kafka.topics.order-cancel}")
    private String orderCancelTopic;
    @Value("${kafka.topics.order-change-destination}")
    private String orderChangeDestination;

}
