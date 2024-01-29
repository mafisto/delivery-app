package com.radkevich.usersservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.geometric.PGpoint;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Координаты заказа")
public class LocationDto implements Serializable {
    @Schema(description = "Координата х", example = "-45")
    @NotBlank(message = "Поле не может быть пустым")
    private double x;
    @Schema(description = "Координата y", example = "15.6")
    @NotBlank(message = "Поле не может быть пустым")
    private double y;

    public LocationDto(PGpoint point) {
        x=point.x;
        y=point.y;
    }
}
