package com.radkevich.usersservice.dto.broker;

import com.radkevich.usersservice.dto.DestinationDto;
import com.radkevich.usersservice.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ChangeDestinationOrderMessage implements Serializable {
    private Integer orderId;
    private LocationDto data;
}
