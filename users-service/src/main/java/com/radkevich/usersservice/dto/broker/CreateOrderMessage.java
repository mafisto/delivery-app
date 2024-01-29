package com.radkevich.usersservice.dto.broker;

import com.radkevich.usersservice.dto.OrderCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CreateOrderMessage implements Serializable {
    private Integer userId;
    private OrderCreateDto data;
}
