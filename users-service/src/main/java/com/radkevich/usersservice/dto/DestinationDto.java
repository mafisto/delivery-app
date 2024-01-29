package com.radkevich.usersservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Координаты доставки")

public class DestinationDto implements Serializable {
    @Schema(description = "Координата х", example = "-45")
    private double x;
    @Schema(description = "Координата y", example = "15.6")
    private double y;
}
