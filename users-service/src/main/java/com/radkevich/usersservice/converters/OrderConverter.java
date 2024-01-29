package com.radkevich.usersservice.converters;

import com.radkevich.usersservice.dto.CouriersDto;
import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderDto;
import com.radkevich.usersservice.entity.CouriersEntity;
import com.radkevich.usersservice.entity.OrdersEntity;
import com.radkevich.usersservice.entity.ParcelsEntity;

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
        orderDto.setCourierName(it.getCourierId() != null ? it.getCourierId().getUsername() : null);
        orderDto.setCourierMobileNumber(it.getCourierId() != null ? it.getCourierId().getMobileNumber() : null);
        orderDto.setStatus(it.getOrderStatusHistoryId() != null ? it.getOrderStatusHistoryId().getStatusId().getStatus() : null);
        orderDto.setStartLocation(it.getOrderLocationId().getStartLocation() != null ? new LocationDto(it.getOrderLocationId().getStartLocation()) : null);
        orderDto.setFinishLocation(it.getOrderLocationId().getFinishLocation() != null ? new LocationDto(it.getOrderLocationId().getFinishLocation()) : null);
        orderDto.setCurrentLocation(it.getOrderLocationId().getCurrentLocation() != null ? new LocationDto(it.getOrderLocationId().getCurrentLocation()) : null);

        orderDto.setFinishTime(it.getFinishTime());
        return orderDto;
    }
}
