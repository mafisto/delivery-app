package com.radkevich.adminservice.repository;

import com.radkevich.adminservice.entity.OrderPathEntity;

public interface OrdersPathMongoRepository {


    /**
     * Получение всех координат следования заказа
     * @param orderId идентификатор заказа
     * @return данные о координатах следования
     */
    OrderPathEntity getOrderPath(Integer orderId);
}
