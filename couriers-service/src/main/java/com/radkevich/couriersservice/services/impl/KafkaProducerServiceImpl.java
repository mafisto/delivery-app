package com.radkevich.couriersservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radkevich.couriersservice.config.KafkaProducerProperties;
import com.radkevich.couriersservice.constants.InfoMessagesConstants;
import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import com.radkevich.couriersservice.dto.LocationDto;
import com.radkevich.couriersservice.dto.broker.ChangeLocationOrderMessage;
import com.radkevich.couriersservice.dto.broker.ChangeOrderStatusMessage;
import com.radkevich.couriersservice.exceptions.SendMessageBrokerException;
import com.radkevich.couriersservice.services.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProducerProperties kafkaProducerProperties;


    @Override
    public void updateLocation(Integer userId, Integer orderId, LocationDto destinationDto) {
        ChangeLocationOrderMessage changeDestinationMessage = new ChangeLocationOrderMessage(userId, orderId, destinationDto);
        sendMessage(changeDestinationMessage, kafkaProducerProperties.getOrderChangeLocation());
    }

    @Override
    public void changeOrderStatus(Integer userId, Integer orderId, ChangeOrderStatusDto dto) {
        ChangeOrderStatusMessage message = new ChangeOrderStatusMessage(userId,orderId, dto.getStatus().getValue());
        sendMessage(message, kafkaProducerProperties.getOrderChangeStatus());
    }


    /**
     * Отправка сообщения в брокер сообщений
     * @param obj  объект для отправки
     * @param topic  имя топика
     */
    @Retryable(maxAttemptsExpression = "${retry.max-attempts}", backoff = @Backoff(delayExpression = "${retry.max-delay}"))
    private void sendMessage(Object obj, String topic) {
        try {
            String msg = objectMapper.writeValueAsString(obj);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, msg);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.debug(InfoMessagesConstants.MESSAGE_SENT, msg, result.getRecordMetadata().offset());
                } else {
                    throw new SendMessageBrokerException(ex);
                }
            });
        } catch (Exception ex) {
            throw new SendMessageBrokerException(ex);
        }

    }

}