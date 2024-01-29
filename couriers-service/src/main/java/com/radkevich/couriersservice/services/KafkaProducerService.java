package com.radkevich.couriersservice.services;

import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import com.radkevich.couriersservice.dto.LocationDto;

public interface KafkaProducerService {

    /**
     * Меняет текущее местоположение заказа
     * @param orderId   идентификатор заказа
     * @param destinationDto    координаты доставки
     */
    void updateLocation(Integer userId, Integer orderId, LocationDto destinationDto);
    /**
     * Меняет статус заказа
     *
     * @param orderId
     * @param dto     данные для смены статуса заказа
     */
    void changeOrderStatus(Integer userId, Integer orderId, ChangeOrderStatusDto dto);
}
