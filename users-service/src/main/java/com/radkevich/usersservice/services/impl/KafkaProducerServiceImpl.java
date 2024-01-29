package com.radkevich.usersservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radkevich.usersservice.config.KafkaProducerProperties;
import com.radkevich.usersservice.constants.InfoMessagesConstants;
import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderCreateDto;
import com.radkevich.usersservice.dto.broker.CancelOrderMessage;
import com.radkevich.usersservice.dto.broker.ChangeDestinationOrderMessage;
import com.radkevich.usersservice.dto.broker.CreateOrderMessage;
import com.radkevich.usersservice.exception.SendMessageBrokerException;
import com.radkevich.usersservice.services.KafkaProducerService;
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
    public void cancelOrder(Integer userId, Integer orderId) {
        CancelOrderMessage cancelOrderMessage = new CancelOrderMessage(userId, orderId);
        sendMessage(cancelOrderMessage, kafkaProducerProperties.getOrderCancelTopic());
    }

    @Override
    public void updateDestination(Integer orderId, LocationDto destinationDto) {
        ChangeDestinationOrderMessage changeDestinationMessage = new ChangeDestinationOrderMessage(orderId, destinationDto);
        sendMessage(changeDestinationMessage, kafkaProducerProperties.getOrderChangeDestination());
    }

    @Override
    public void createOrder(Integer userId, OrderCreateDto orderCreateDto) {
        CreateOrderMessage createOrderMessage = new CreateOrderMessage(userId, orderCreateDto);
        sendMessage(createOrderMessage, kafkaProducerProperties.getOrderCreateTopic());
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