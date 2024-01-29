package com.radkevich.oderservice.dto.broker;

import com.radkevich.oderservice.dto.OrderCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderMessage implements Serializable {
    private Integer userId;
    private OrderCreateDto data;
}
