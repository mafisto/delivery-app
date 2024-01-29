package com.radkevich.adminservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные какому курьеру передать заказ")
public class AssignCourierToOrderDto {
    @Schema(description = "Идентификатор курьера", example = "3")
    @NotBlank(message = "Поле не может быть пустым")
    private Integer courierId;

}
