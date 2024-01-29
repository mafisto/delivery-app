package com.radkevich.adminservice.converters;


import com.radkevich.adminservice.dto.CourierStatus;
import com.radkevich.adminservice.dto.CourierDto;
import com.radkevich.adminservice.entity.CouriersEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CourierConverter {

    public static List<CourierDto> convertToDtos(List<CouriersEntity> entities) {
        List<CourierDto> dtos = entities.stream().map(it-> CourierConverter.convertToDto(it)).collect(Collectors.toList());
        return dtos;
    }

    public static CourierDto convertToDto(CouriersEntity it) {
        CourierDto dto = new CourierDto();
        dto.setUserName(it.getUsername());
        dto.setEmail(it.getEmail());
        dto.setMobileNumber(it.getMobileNumber());
        dto.setActive(it.getActive());
        dto.setStatus(it.getCourierStatusId()!=null? CourierStatus.getByValue(it.getCourierStatusId().getStatus()): null);
        return dto;
    }
}
