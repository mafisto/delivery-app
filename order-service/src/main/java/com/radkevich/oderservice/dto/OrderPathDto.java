package com.radkevich.oderservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные о перемещении заказа")
public class OrderPathDto {
    @Schema(description = "Идентификатор заказа")
    private Integer orderId;
    @Schema(description = "Массив координат")
    private List<List<Double>> coordinates;
}
