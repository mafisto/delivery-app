package com.radkevich.adminservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Детали курьера")
public class CourierDto {

    @Schema(description = "Имя курьера", example = "Jon")
    private String userName;
    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    private String email;

    @Schema(description = "Телефон", example = "88005553535")
    private String mobileNumber;

    @Schema(description = "Активный ли аккаунт", example = "true")
    private Boolean active;

    @Schema(description = "Статус курьера", example = "Coffee Break")
    private CourierStatus status;


}
