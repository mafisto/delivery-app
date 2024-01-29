package com.radkevich.couriersservice.services.impl;

import com.radkevich.couriersservice.constants.InfoMessagesConstants;
import com.radkevich.couriersservice.converters.OrderConverter;
import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import com.radkevich.couriersservice.dto.LocationDto;
import com.radkevich.couriersservice.dto.OrderDto;
import com.radkevich.couriersservice.dto.OrderStatuses;
import com.radkevich.couriersservice.entity.OrdersEntity;
import com.radkevich.couriersservice.exceptions.ChangeOrderStatusException;
import com.radkevich.couriersservice.exceptions.OrderNotFoundException;
import com.radkevich.couriersservice.repository.OrdersRepository;
import com.radkevich.couriersservice.security.CustomUserDetails;
import com.radkevich.couriersservice.services.KafkaProducerService;
import com.radkevich.couriersservice.services.OrdersService;
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
    public List<OrderDto> getOrders() {
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
    public OrderDto getOrder(Integer orderId) {
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
    public void updateLocation(Integer orderId, LocationDto destinationDto) {
        Optional<OrdersEntity> order = ordersRepository.getOrdersDetails(getUser().getUserId(), orderId);
        if (order.isPresent()) {
            kafkaProducerService.updateLocation(getUser().getUserId(), orderId, destinationDto);
            log.debug(InfoMessagesConstants.UPDATE_ORDER_LOCATION, destinationDto);
        } else {
            throw new OrderNotFoundException(orderId);
        }

    }

    @Override
    public void changeOrderStatus(Integer orderId, ChangeOrderStatusDto dto) {
        Optional<OrdersEntity> order = ordersRepository.getOrdersDetails(getUser().getUserId(), orderId);
        if (order.isPresent()) {
            String status = order.get().getOrderStatusHistoryId().getStatusId().getStatus();
            boolean isAllowToChangeStatus = status.equals(OrderStatuses.CREATED.getValue())
                    || status.equals(OrderStatuses.PROCESSING.getValue())
                    || status.equals(OrderStatuses.IN_TRANSIT.getValue());
            if (isAllowToChangeStatus) {
                kafkaProducerService.changeOrderStatus(getUser().getUserId(), orderId, dto);
                log.debug(InfoMessagesConstants.CHANGE_STATUS_ORDER, orderId, dto.getStatus());
            } else {
                throw new ChangeOrderStatusException();
            }
        } else {
            throw new OrderNotFoundException(orderId);
        }
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
