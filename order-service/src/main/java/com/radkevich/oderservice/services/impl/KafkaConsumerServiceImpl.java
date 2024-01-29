package com.radkevich.oderservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radkevich.oderservice.constants.InfoMessagesConstants;
import com.radkevich.oderservice.dto.broker.AssignCourierToOrderMessage;
import com.radkevich.oderservice.dto.broker.CancelOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeDestinationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeLocationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeOrderStatusMessage;
import com.radkevich.oderservice.dto.broker.CreateCourierMessage;
import com.radkevich.oderservice.dto.broker.CreateOrderMessage;
import com.radkevich.oderservice.entity.OrdersEntity;
import com.radkevich.oderservice.services.CourierService;
import com.radkevich.oderservice.services.KafkaConsumerService;
import com.radkevich.oderservice.services.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private final ObjectMapper objectMapper;
    private final OrdersService ordersService;
    private final CourierService courierService;

    @Override
    @KafkaListener(topics = "${kafka.topics.order-create}", groupId = "${spring.kafka.consumer.group-id}")
    public void createOrder(String msg) {
        log.info(InfoMessagesConstants.CREATE_ORDER_MESSAGE, msg);
        CreateOrderMessage data = readMessage(msg, CreateOrderMessage.class);
        OrdersEntity order = ordersService.createOrder(data);
        log.info(InfoMessagesConstants.ORDER_CREATED_MESSAGE, order);
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.order-change-destination}", groupId = "${spring.kafka.consumer.group-id}")
    public void changeOrderDestination(String msg) {
        log.info(InfoMessagesConstants.CHANGE_DESTINATION_ORDER_MESSAGE, msg);
        ChangeDestinationOrderMessage data = readMessage(msg, ChangeDestinationOrderMessage.class);
        ordersService.changeOrderDestination(data);
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.order-cancel}", groupId = "${spring.kafka.consumer.group-id}")
    public void cancelOrder(String msg) {
        log.info(InfoMessagesConstants.CANCEL_ORDER_MESSAGE, msg);
        CancelOrderMessage data = readMessage(msg, CancelOrderMessage.class);
        ordersService.cancelOrder(data);

    }

    @Override
    @KafkaListener(topics = "${kafka.topics.order-change-status}", groupId = "${spring.kafka.consumer.group-id}")
    public void changeOrderStatus(String msg) {
        log.info(InfoMessagesConstants.CHANGE_STATUS_ORDER_MESSAGE, msg);
        ChangeOrderStatusMessage data = readMessage(msg, ChangeOrderStatusMessage.class);
        ordersService.changeOrderStatus(data);
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.order-assign-courier}", groupId = "${spring.kafka.consumer.group-id}")
    public void assignCourierToOrder(String msg) {
        log.info(InfoMessagesConstants.ASSIGN_COURIER_ORDER_MESSAGE, msg);
        AssignCourierToOrderMessage data = readMessage(msg, AssignCourierToOrderMessage.class);
        ordersService.assignCourierToOrder(data);
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.order-create-courier}", groupId = "${spring.kafka.consumer.group-id}")
    public void createCourier(String msg) {
        log.info(InfoMessagesConstants.CREATE_COURIER_MESSAGE, msg);
        CreateCourierMessage data = readMessage(msg, CreateCourierMessage.class);
        courierService.createCourier(data);
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.order-change-location}", groupId = "${spring.kafka.consumer.group-id}")
    public void changeOrderCoordinates(String msg) {
        log.info(InfoMessagesConstants.CHANGE_ORDER_LOCATION_MESSAGE, msg);
        ChangeLocationOrderMessage data = readMessage(msg, ChangeLocationOrderMessage.class);
        ordersService.changeOrderCoordinates(data);
    }

    /**
     * Конвертирует сообщение из топика к заданному типу
     *
     * @param msg
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T readMessage(String msg, Class<T> clazz) {
        try {
            T obj = (T) objectMapper.readValue(msg, clazz);
            return obj;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}