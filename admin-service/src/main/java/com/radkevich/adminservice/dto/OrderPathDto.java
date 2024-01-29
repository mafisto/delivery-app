package com.radkevich.adminservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
