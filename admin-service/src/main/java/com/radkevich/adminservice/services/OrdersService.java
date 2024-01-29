package com.radkevich.adminservice.services;

import com.radkevich.adminservice.dto.AssignCourierToOrderDto;
import com.radkevich.adminservice.dto.ChangeOrderStatusDto;
import com.radkevich.adminservice.dto.OrderDto;
import com.radkevich.adminservice.dto.OrderLocationDto;
import com.radkevich.adminservice.dto.OrderPathDto;

import java.util.List;

public interface OrdersService {

    /**
     * Получение списка все заказов
     *
     * @return список заказов
     */
    List<OrderDto> getOrders();

    /**
     * Возвращает заказ
     *
     * @param orderId идентификатор ордера
     * @return заказ
     */
    OrderDto getOrder(Integer orderId);

    /**
     * Меняет статус заказа
     *
     * @param orderId идентификатор заказа
     * @param dto     данные для смены статуса заказа
     */
    void changeOrderStatus(Integer orderId, ChangeOrderStatusDto dto);

    /**
     * Назначение курьера заказу
     *
     * @param orderId идентификатор заказа
     * @param dto     данные о курьере
     */
    void assignCourierToOrder(Integer orderId, AssignCourierToOrderDto dto);

    /**
     * Возвращает текущую локацию заказа
     *
     * @param orderId идентификатор заказа
     */
    OrderLocationDto getOrderLocation(Integer orderId);

    /**
     * Получение всех координат следования заказа
     *
     * @param orderId идентификатор заказа
     * @return данные о координатах следования
     */
    OrderPathDto getOrderPath(Integer orderId);


}
