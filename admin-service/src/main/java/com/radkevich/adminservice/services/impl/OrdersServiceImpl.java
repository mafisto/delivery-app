package com.radkevich.adminservice.services.impl;


import com.radkevich.adminservice.constants.InfoMessagesConstants;
import com.radkevich.adminservice.converters.OrderConverter;
import com.radkevich.adminservice.dto.AssignCourierToOrderDto;
import com.radkevich.adminservice.dto.ChangeOrderStatusDto;
import com.radkevich.adminservice.dto.OrderDto;
import com.radkevich.adminservice.dto.OrderLocationDto;
import com.radkevich.adminservice.dto.OrderPathDto;
import com.radkevich.adminservice.entity.CouriersEntity;
import com.radkevich.adminservice.entity.OrderLocationEntity;
import com.radkevich.adminservice.entity.OrderPathEntity;
import com.radkevich.adminservice.entity.OrdersEntity;
import com.radkevich.adminservice.exceptions.AssignCourierToOrderException;
import com.radkevich.adminservice.exceptions.OrderNotFoundException;
import com.radkevich.adminservice.repository.CouriersRepository;
import com.radkevich.adminservice.repository.OrderLocationRepository;
import com.radkevich.adminservice.repository.OrdersPathMongoRepository;
import com.radkevich.adminservice.repository.OrdersRedisRepository;
import com.radkevich.adminservice.repository.OrdersRepository;
import com.radkevich.adminservice.security.CustomUserDetails;
import com.radkevich.adminservice.services.KafkaProducerService;
import com.radkevich.adminservice.services.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final CouriersRepository couriersRepository;
    private final KafkaProducerService kafkaProducerService;
    private final OrderLocationRepository orderLocationRepository;
    private final OrdersPathMongoRepository ordersPathMongoRepository;
    private final OrdersRedisRepository ordersRedisRepository;

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> orders = ordersRedisRepository.getOrders();
        if(!orders.isEmpty()){
            return orders;
        } else {
            List<OrdersEntity> entities = ordersRepository.findAll();
            orders = OrderConverter.convertToDtos(entities);
            ordersRedisRepository.putOrders(orders);
        }
        log.debug(InfoMessagesConstants.ORDERS_RECEIVED, orders.size());
        return orders;
    }

    @Override
    public OrderDto getOrder(Integer orderId) {
        OrderDto order = ordersRedisRepository.get(orderId);
        if (order != null) {
            return order;
        } else {
            Optional<OrdersEntity> entities = ordersRepository.findById(orderId);
            if (entities.isPresent()) {
                order = OrderConverter.convertToDto(entities.get());
                ordersRedisRepository.put(orderId, order);
            } else {
                throw new OrderNotFoundException(orderId);
            }
        }
        log.debug(InfoMessagesConstants.ORDER_RECEIVED, order);
        return order;
    }

    @Override
    public void changeOrderStatus(Integer orderId, ChangeOrderStatusDto dto) {
        Optional<OrdersEntity> order = ordersRepository.findById(orderId);
        if (order.isPresent()) {
            String status = order.get().getOrderStatusHistoryId().getStatusId().getStatus();
            kafkaProducerService.changeOrderStatus(getUser().getUserId(), orderId, dto);
            log.debug(InfoMessagesConstants.CHANGE_STATUS_ORDER, orderId, dto.getStatus());
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    @Override
    public void assignCourierToOrder(Integer orderId, AssignCourierToOrderDto dto) {
        Optional<OrdersEntity> order = ordersRepository.findById(orderId);
        Optional<CouriersEntity> courier = couriersRepository.findById(dto.getCourierId());

        if (order.isEmpty() || courier.isEmpty()) {
            throw new AssignCourierToOrderException(orderId, dto.getCourierId());
        }
        order.get().setCourierId(courier.get());
        kafkaProducerService.assignCourierToOrder(getUser().getUserId(), orderId, dto.getCourierId());
    }

    @Override
    public OrderLocationDto getOrderLocation(Integer orderId) {
        Optional<OrdersEntity> order = ordersRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }
        OrderLocationEntity orderLocationId = order.get().getOrderLocationId();
        if (orderLocationId != null) {
            Optional<OrderLocationEntity> orderLocation = orderLocationRepository.findById(orderLocationId.getId());
            return OrderConverter.convertToDto(orderLocation.get());
        } else {
            return new OrderLocationDto();
        }
    }

    @Override
    public OrderPathDto getOrderPath(Integer orderId) {
        OrderPathEntity orderPathEntity = ordersPathMongoRepository.getOrderPath(orderId);
        if (orderPathEntity != null) {
            return OrderConverter.toDto(orderPathEntity);
        } else return null;
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
