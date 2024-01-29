package com.radkevich.usersservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Создание Заказа")
public class OrderCreateDto {
    @Schema(description = "Название посылки", example = "Food")
    @Size(min = 2, max = 50, message = "Название должно содержать от 3 до 50 символов")
    @NotBlank(message = "Поле не может быть пустым")
    private String itemName;
    @Schema(description =  "Конечные координаты посылки", example = "(4,5)")
    @NotBlank(message = "Поле не может быть пустым")
    private LocationDto destination;
    @Schema(description = "Начальные координаты посылки", example = "(1,2)")
    @NotBlank(message = "Поле не может быть пустым")
    private LocationDto startLocation;

}