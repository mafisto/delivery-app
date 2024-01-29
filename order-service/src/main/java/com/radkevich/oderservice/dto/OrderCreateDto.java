package com.radkevich.oderservice.dto;

import lombok.Builder;
import lombok.Data;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.geo.Point;

@Data
@Builder
public class OrderCreateDto {
    private String itemName;
    private LocationDto destination;
    private LocationDto startLocation;

}
