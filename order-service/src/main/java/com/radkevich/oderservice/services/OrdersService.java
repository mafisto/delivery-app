package com.radkevich.oderservice.services;

import com.radkevich.oderservice.dto.OrderStatuses;
import com.radkevich.oderservice.dto.broker.AssignCourierToOrderMessage;
import com.radkevich.oderservice.dto.broker.CancelOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeDestinationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeLocationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeOrderStatusMessage;
import com.radkevich.oderservice.dto.broker.CreateOrderMessage;
import com.radkevich.oderservice.entity.OrdersEntity;

public interface OrdersService {

    /**
     * Создаёт новый заказ
     *
     * @param data
     * @return
     */
    OrdersEntity createOrder(CreateOrderMessage data);

    /**
     * Изменяет конечный пункт заказа
     *
     * @param data
     */
    void changeOrderDestination(ChangeDestinationOrderMessage data);


    /**
     * Меняет статус заказа на {@link OrderStatuses#CANCELLED}
     *
     * @param data
     */
    void cancelOrder(CancelOrderMessage data);


    /**
     * Меняет статус заказа
     *
     * @param data
     */
    void changeOrderStatus(ChangeOrderStatusMessage data);

    /**
     * Меняет координаты заказа
     *
     * @param data
     */
    void changeOrderCoordinates(ChangeLocationOrderMessage data);

    /**
     * Назначает курьера заказу
     *
     * @param data
     */
    void assignCourierToOrder(AssignCourierToOrderMessage data);
}
