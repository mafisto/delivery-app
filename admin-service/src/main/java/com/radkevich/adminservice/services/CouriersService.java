package com.radkevich.adminservice.services;

import com.radkevich.adminservice.dto.CourierDto;
import com.radkevich.adminservice.dto.CreateCourierDto;

import java.util.List;

public interface CouriersService {
    /**
     * Создание нового курьера
     *
     * @param dto данные о курьере
     */
    void createCourier(CreateCourierDto dto);

    /**
     * Возвращает курьера
     *
     * @param id идентификатор курьера
     * @return модель курьера
     */
    CourierDto getCourier(Integer id);

    /**
     * Список всех курьеров со статусами
     *
     * @return список курьеров
     */
    List<CourierDto> getAllCouriers();
}
