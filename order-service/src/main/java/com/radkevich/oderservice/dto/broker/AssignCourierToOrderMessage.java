package com.radkevich.oderservice.dto.broker;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignCourierToOrderMessage implements Serializable {
    private Integer agentId;
    private Integer courierId;
    private Integer orderId;
}
