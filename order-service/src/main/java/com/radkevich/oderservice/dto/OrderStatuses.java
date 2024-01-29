package com.radkevich.oderservice.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public enum OrderStatuses {

    CREATED("Created"),
    PROCESSING("Processing"),
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private final String value;

    OrderStatuses(String value) {
        this.value = value;
    }
}
