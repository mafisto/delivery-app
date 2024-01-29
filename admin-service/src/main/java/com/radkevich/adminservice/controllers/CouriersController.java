package com.radkevich.adminservice.controllers;


import com.radkevich.adminservice.constants.InfoMessagesConstants;
import com.radkevich.adminservice.dto.CourierDto;
import com.radkevich.adminservice.dto.CreateCourierDto;
import com.radkevich.adminservice.services.CouriersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = CouriersController.ROOT_WEB_CONTEXT)
@RequiredArgsConstructor
@Tag(name = "Курьеры")
@SecurityRequirement(name = "SwaggerAuthorization")
public class CouriersController {
    public static final String ROOT_WEB_CONTEXT = "/api/v1/couriers";
    private final CouriersService couriersService;

    @Operation(summary = "Создание нового курьера ")
    @PostMapping
    public ResponseEntity<String> createCourier(@RequestBody @Valid CreateCourierDto request) {
        log.debug(InfoMessagesConstants.CREATE_COURIER, request.getUsername());
        couriersService.createCourier(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Список всех курьеров со статусами")
    @GetMapping
    public ResponseEntity<List<CourierDto>> getAllCouriers() {
        List<CourierDto> couriers = couriersService.getAllCouriers();
        log.debug(InfoMessagesConstants.GET_ALL_COURIERS, couriers.size());
        return new ResponseEntity<>(couriers, HttpStatus.OK);
    }

    @Operation(summary = "Возвращает курьера")
    @GetMapping("/{id}")
    public ResponseEntity<CourierDto> getCourier(@PathVariable("id") Integer courierId) {
        CourierDto couriers = couriersService.getCourier(courierId);
        log.debug(InfoMessagesConstants.GET_COURIER, courierId);
        return new ResponseEntity<>(couriers, HttpStatus.OK);
    }

}