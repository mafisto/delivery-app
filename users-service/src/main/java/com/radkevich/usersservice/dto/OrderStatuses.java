package com.radkevich.usersservice.dto;

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
