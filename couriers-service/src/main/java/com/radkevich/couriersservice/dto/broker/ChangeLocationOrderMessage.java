package com.radkevich.couriersservice.dto.broker;

import com.radkevich.couriersservice.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ChangeLocationOrderMessage implements Serializable {
    Integer userId;
    private Integer orderId;
    private LocationDto data;
}
