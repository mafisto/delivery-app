package com.radkevich.adminservice.dto;


import lombok.Data;

@Data
public class OrderLocationDto {
    private Integer id;
    private LocationDto startLocation;
    private LocationDto finishLocation;
    private LocationDto currentLocation;

}
