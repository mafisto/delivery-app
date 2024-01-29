package com.radkevich.adminservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Schema(description = "Данные о заказе")
public class OrderDto implements Serializable {
    @Schema(description = "Идентификатор заказа")
    private Integer id;
    @Schema(description = "Время создания заказа")
    private Timestamp createdTime;
    @Schema(description = "Время начала  доставки заказа")
    private Timestamp startTime;
    @Schema(description = "Время окончания доставки заказа")
    private Timestamp finishTime;
    @Schema(description = "Имя курьера")
    private String courierName;
    @Schema(description = "Мобильный номер курьера курьера")
    private String courierMobileNumber;
    @Schema(description = "Статус заказа")
    private String status;
    @Schema(description = "Данные о пункте отправления")
    private LocationDto startLocation;
    @Schema(description = "Данные о пункте доставки")
    private LocationDto finishLocation;
    @Schema(description = "Данные о текущей локации")
    private LocationDto currentLocation;
    @Schema(description = "Название посылки")
    private String parcelName;

}
