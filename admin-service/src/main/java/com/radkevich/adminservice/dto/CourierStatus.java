package com.radkevich.adminservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Cтатус курьера")
public enum CourierStatus {

    AVAILABLE("Available"),
    IN_TRANSIT("In Transit"),
    COFFE_BREAK("Coffee Break");


    @Schema(description = "Новый статус", example = "'Available'")
    private final String value;

    CourierStatus(String value) {
        this.value = value;
    }

    public static CourierStatus getByValue(String val){
        for(CourierStatus v : values()){
            if(v.value.equals(val)){
                return v;
            }
        }
        return null;
    }
}
