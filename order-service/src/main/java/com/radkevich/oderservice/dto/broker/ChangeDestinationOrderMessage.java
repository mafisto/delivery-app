package com.radkevich.oderservice.dto.broker;

import com.radkevich.oderservice.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.postgresql.geometric.PGpoint;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeDestinationOrderMessage implements Serializable {
    private Integer orderId;
    private LocationDto data;
}
