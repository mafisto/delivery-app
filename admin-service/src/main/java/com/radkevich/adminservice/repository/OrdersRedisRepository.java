package com.radkevich.adminservice.repository;

import com.radkevich.adminservice.dto.OrderDto;

import java.util.List;

public interface OrdersRedisRepository {
    String ORDER_KEY = "parcel:admin-service:order";
    String ORDERS_LIST_KEY = "parcel:admin-service:orders-list";
    /**
     * Проверяет, есть ли требуемый заказ в кэше
     * @param orderId идентификатор заказа
     * @return true/false
     */
    boolean hasCachedOrder(Integer orderId);

    /**
     * сохраняет заказ в кэш
     * @param orderId идентификатор заказа
     * @param dto объект заказа
     */
    void put(Integer orderId, OrderDto dto);
    /**
     * Получает заказ из кэша
     * @param orderId идентификатор заказа
     * @return
     */
    OrderDto get(Integer orderId);

    /**
     * Получает список всех заказов
     *
     * @return список заказов
     */
    List<OrderDto> getOrders();

    /**
     * сохраняет список заказов в кэш
     * @param orders список заказов
     */
    void putOrders(List<OrderDto> orders);
}
