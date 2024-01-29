package com.radkevich.adminservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для смены статуса заказа")
public class ChangeOrderStatusDto {

    @Schema(description = "Новый статус", example = "DELIVERED")
    @NotBlank(message = "Статус не может быть пустым")
    private OrderStatuses status;

}
