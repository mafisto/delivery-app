package com.radkevich.adminservice.services;

import com.radkevich.adminservice.config.KafkaProducerProperties;
import com.radkevich.adminservice.constants.InfoMessagesConstants;
import com.radkevich.adminservice.dto.AssignCourierToOrderDto;
import com.radkevich.adminservice.dto.ChangeOrderStatusDto;
import com.radkevich.adminservice.dto.CreateCourierDto;
import com.radkevich.adminservice.dto.broker.AssignCourierToOrderMessage;
import com.radkevich.adminservice.dto.broker.ChangeOrderStatusMessage;
import com.radkevich.adminservice.dto.broker.CreateCourierMessage;
import com.radkevich.adminservice.exceptions.SendMessageBrokerException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class KafkaProducerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProducerProperties kafkaProducerProperties;

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


    /**
     * Меняет статус заказа
     *
     * @param orderId
     * @param dto     данные для смены статуса заказа
     */
    public void changeOrderStatus(Integer userId, Integer orderId, ChangeOrderStatusDto dto) {
        ChangeOrderStatusMessage message = new ChangeOrderStatusMessage(userId,orderId, dto.getStatus().getValue());
        sendMessage(message, kafkaProducerProperties.getOrderChangeStatus());
    }


    public void createCourier(CreateCourierDto dto) {
        CreateCourierMessage msg = new CreateCourierMessage(dto);
        sendMessage(msg, kafkaProducerProperties.getCreateCourier());
    }

    public void assignCourierToOrder(Integer adminId, Integer orderId, Integer courierId) {
        AssignCourierToOrderMessage msg = new AssignCourierToOrderMessage(adminId, courierId, orderId);
        sendMessage(msg, kafkaProducerProperties.getOrderAssignCourier());
    }
}