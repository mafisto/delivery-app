package com.radkevich.oderservice.dto;

import lombok.Getter;

@Getter
public enum PersonRoles {
    ADMIN("admin"),
    USER("user"),
    COURIER("courier");

    private final String value;

    PersonRoles(String value) {
        this.value = value;
    }
}
