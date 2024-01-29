package com.radkevich.usersservice.services;

import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderCreateDto;

public interface KafkaProducerService {

    /**
     * Отмена ордера
     *
     * @param userId  идентификатор пользователя
     * @param orderId идентификатор заказа
     */
    void cancelOrder(Integer userId, Integer orderId);

    /**
     * Меняет координаты доставки
     *
     * @param orderId        идентификатор заказа
     * @param destinationDto координаты доставки
     */
    void updateDestination(Integer orderId, LocationDto destinationDto);


    /**
     * Создает новый заказ
     *
     * @param userId
     * @param orderCreateDto данне для создания заказа
     */
    void createOrder(Integer userId, OrderCreateDto orderCreateDto);


}
