package com.radkevich.couriersservice.converters;


import com.radkevich.couriersservice.dto.LocationDto;
import com.radkevich.couriersservice.dto.OrderDto;
import com.radkevich.couriersservice.entity.OrdersEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static List<OrderDto> convertToDtos(Collection<OrdersEntity> entities) {
        return entities.stream().map(OrderConverter::convertToDto).collect(Collectors.toList());
    }


    public static OrderDto convertToDto(OrdersEntity it) {
        if (it == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(it.getId());

        orderDto.setParcelName(it.getParcelId() != null ? it.getParcelId().getItemName() : null);
        orderDto.setCourierName(it.getCourierId() != null ? it.getCourierId().getUsername() : null);

        orderDto.setStartTime(it.getStartTime());
        orderDto.setCreatedTime(it.getCreatedTime());
        orderDto.setCourierMobileNumber(it.getCourierId() != null ? it.getCourierId().getMobileNumber() : null);
        orderDto.setStatus(it.getOrderStatusHistoryId() != null ? it.getOrderStatusHistoryId().getStatusId().getStatus() : null);
        orderDto.setStartLocation(it.getOrderLocationId().getStartLocation() != null ? new LocationDto(it.getOrderLocationId().getStartLocation()) : null);
        orderDto.setFinishLocation(it.getOrderLocationId().getFinishLocation() != null ? new LocationDto(it.getOrderLocationId().getFinishLocation()) : null);
        orderDto.setCurrentLocation(it.getOrderLocationId().getCurrentLocation() != null ? new LocationDto(it.getOrderLocationId().getCurrentLocation()) : null);

        orderDto.setFinishTime(it.getFinishTime());
        return orderDto;
    }
}
