package com.radkevich.couriersservice.services;

import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import com.radkevich.couriersservice.dto.LocationDto;
import com.radkevich.couriersservice.dto.OrderDto;

import java.util.List;

public interface OrdersService {
    /**
     * Получение списка заказов для пользователя
     *
     * @return список зазаков
     */
    public List<OrderDto> getOrders();

    /**
     * Возвращает заказ пользователя
     *
     * @param orderId идентификатор ордера
     * @return заказ пользователя
     */
    public OrderDto getOrder(Integer orderId);

    /**
     * Меняет текущее местоположение заказа
     *
     * @param orderId        идентификатор заказа
     * @param destinationDto координаты заказа
     */
    public void updateLocation(Integer orderId, LocationDto destinationDto);

    /**
     * Меняет статус заказа
     *
     * @param orderId
     * @param dto     данные для смены статуса заказа
     */
    public void changeOrderStatus(Integer orderId, ChangeOrderStatusDto dto);


}
