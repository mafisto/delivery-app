package com.radkevich.couriersservice.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Cтатус заказа")
public enum OrderStatuses {

    CREATED("Created"),
    PROCESSING("Processing"),
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");


    @Schema(description = "Новый статус", example = "Delivered")
    private final String value;

    OrderStatuses(String value) {
        this.value = value;
    }
}
