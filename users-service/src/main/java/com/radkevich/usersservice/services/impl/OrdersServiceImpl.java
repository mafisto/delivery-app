package com.radkevich.usersservice.services.impl;

import com.radkevich.usersservice.constants.InfoMessagesConstants;
import com.radkevich.usersservice.converters.OrderConverter;
import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderCreateDto;
import com.radkevich.usersservice.dto.OrderDto;
import com.radkevich.usersservice.dto.OrderStatuses;
import com.radkevich.usersservice.entity.OrdersEntity;
import com.radkevich.usersservice.exception.CancelOrderException;
import com.radkevich.usersservice.exception.OrderNotFoundException;
import com.radkevich.usersservice.repository.OrdersRepository;
import com.radkevich.usersservice.security.CustomUserDetails;
import com.radkevich.usersservice.services.KafkaProducerService;
import com.radkevich.usersservice.services.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public List<OrderDto> getUserOrders() {
        Optional<List<OrdersEntity>> entities = ordersRepository.getAllOrdersDetails(getUser().getUserId());
        List<OrderDto> orders;
        if (entities.isPresent()) {
            orders = OrderConverter.convertToDtos(entities.get());
            log.debug(InfoMessagesConstants.ORDERS_RECEIVED, orders.size());
        } else {
            orders = new ArrayList<>();
        }
        return orders;
    }

    @Override
    public OrderDto getUserOrder(Integer orderId) {
        Optional<OrdersEntity> entities = ordersRepository.getOrdersDetails(getUser().getUserId(), orderId);
        OrderDto order;
        if (entities.isPresent()) {
            order = OrderConverter.convertToDto(entities.get());
            log.debug(InfoMessagesConstants.ORDER_RECEIVED, order);
            return order;
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Override
    public void updateDestination(Integer orderId, LocationDto destinationDto) {
        kafkaProducerService.updateDestination(orderId, destinationDto);
        log.debug(InfoMessagesConstants.UPDATE_ORDER_DEST, destinationDto);
    }

    @Override
    public void cancelOrder(Integer orderId) {
        Optional<OrdersEntity> order = ordersRepository.getOrdersDetails(getUser().getUserId(), orderId);
        if (order.isPresent()) {
            boolean isAllowToChangeStatus = order.get().getOrderStatusHistoryId().getStatusId()
                    .getStatus().equals(OrderStatuses.CREATED.getValue());
            if (isAllowToChangeStatus) {
                kafkaProducerService.cancelOrder(getUser().getUserId(), orderId);
                log.debug(InfoMessagesConstants.CANCEL_ORDER, orderId);
            } else {
                throw new CancelOrderException();
            }
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Override
    public void createOrder(OrderCreateDto orderCreateDto) {
        kafkaProducerService.createOrder(getUser().getUserId(), orderCreateDto);
        log.debug(InfoMessagesConstants.CREATE_ORDER, orderCreateDto);
    }

    /**
     * Получение авторизованного пользователя из сессии
     *
     * @return детали пользователя
     */
    private CustomUserDetails getUser() {
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details;
    }

}
