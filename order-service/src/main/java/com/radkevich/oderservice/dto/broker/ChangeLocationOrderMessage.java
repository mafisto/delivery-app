package com.radkevich.oderservice.dto.broker;

import com.radkevich.oderservice.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeLocationOrderMessage implements Serializable {
    Integer userId;
    private Integer orderId;
    private LocationDto data;
}
