package com.radkevich.couriersservice.controllers;


import com.radkevich.couriersservice.constants.MessageConstants;
import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import com.radkevich.couriersservice.dto.LocationDto;
import com.radkevich.couriersservice.dto.OrderDto;
import com.radkevich.couriersservice.services.OrdersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = OrdersController.ROOT_WEB_CONTEXT)
@RequiredArgsConstructor
@Tag(name = "Заказы")
@SecurityRequirement(name = "SwaggerAuthorization")
public class OrdersController {
    public static final String ROOT_WEB_CONTEXT = "/api/v1/orders";
    private final OrdersService ordersService;


    @GetMapping()
    public ResponseEntity<List<OrderDto>> getUserOrders() {
        List<OrderDto> orders = ordersService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderDetails(@PathVariable("id") Integer orderId) {
        OrderDto userOrder = ordersService.getOrder(orderId);
        return ResponseEntity.ofNullable(userOrder);
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<String> updateOrderDestination(@PathVariable("id") Integer orderId, @RequestBody @Valid LocationDto destinationDto) {
        ordersService.updateLocation(orderId, destinationDto);
        return new ResponseEntity<>(MessageConstants.ORDER_LOCATION_UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Integer orderId, @RequestBody @Valid ChangeOrderStatusDto dto) {
        ordersService.changeOrderStatus(orderId, dto);
        return new ResponseEntity<>(MessageConstants.ORDER_STATUS_UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

}