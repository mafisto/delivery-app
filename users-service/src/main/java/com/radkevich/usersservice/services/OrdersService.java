package com.radkevich.usersservice.services;

import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderCreateDto;
import com.radkevich.usersservice.dto.OrderDto;

import java.util.List;

public interface OrdersService {
    /**
     * Получение списка заказов для пользователя
     *
     * @return список зазаков
     */
    List<OrderDto> getUserOrders();

    /**
     * Возвращает заказ пользователя
     *
     * @param orderId идентификатор ордера
     * @return заказ пользователя
     */
    OrderDto getUserOrder(Integer orderId);




    /**
     * Меняет координаты доставки
     *
     * @param orderId        идентификатор заказа
     * @param destinationDto координаты доставки
     */
    void updateDestination(Integer orderId, LocationDto destinationDto);


    /**
     * Отменяет заказ
     *
     * @param orderId идентификатор заказа
     */
    void cancelOrder(Integer orderId);



    /**
     * Создает новый заказ
     *
     * @param orderCreateDto данне для создания заказа
     */
     void createOrder(OrderCreateDto orderCreateDto);

}
