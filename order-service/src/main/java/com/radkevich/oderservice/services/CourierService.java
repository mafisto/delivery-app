package com.radkevich.oderservice.services;

import com.radkevich.oderservice.dto.broker.CreateCourierMessage;
import org.springframework.transaction.annotation.Transactional;

public interface CourierService {
    /**
     * Создаёт курьера
     * @param msg   сообщение из топика
     */
    void createCourier(CreateCourierMessage msg);
}
