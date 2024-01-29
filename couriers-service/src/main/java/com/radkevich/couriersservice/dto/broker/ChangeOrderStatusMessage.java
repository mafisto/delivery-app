package com.radkevich.couriersservice.dto.broker;

import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ChangeOrderStatusMessage implements Serializable {
    private Integer personId;
    private Integer orderId;
    private String status;

}
