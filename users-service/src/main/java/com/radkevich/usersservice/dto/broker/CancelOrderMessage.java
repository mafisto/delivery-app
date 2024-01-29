package com.radkevich.usersservice.dto.broker;

import com.radkevich.usersservice.dto.DestinationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CancelOrderMessage implements Serializable {
    private Integer userId;
    private Integer orderId;
}
