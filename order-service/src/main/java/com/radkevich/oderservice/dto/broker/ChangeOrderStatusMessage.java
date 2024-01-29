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
public class ChangeOrderStatusMessage implements Serializable {
    private Integer personId;
    private Integer orderId;
    private String status;
}
