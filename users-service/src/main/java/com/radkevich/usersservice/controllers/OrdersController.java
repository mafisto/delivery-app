package com.radkevich.usersservice.controllers;

import com.radkevich.usersservice.constants.MessageConstants;
import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderCreateDto;
import com.radkevich.usersservice.dto.OrderDto;
import com.radkevich.usersservice.services.OrdersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(OrdersController.ROOT_WEB_CONTEXT)
@RequiredArgsConstructor
@SecurityRequirement(name = "SwaggerAuthorization")
@Tag(name = "Заказы")
public class OrdersController {
    public static final String ROOT_WEB_CONTEXT = "/api/v1/orders";

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid OrderCreateDto orderCreateDto) {
        ordersService.createOrder(orderCreateDto);
        return new ResponseEntity<>(MessageConstants.ORDER_CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/destination")
    public ResponseEntity<String> updateOrderDestination(@PathVariable("id") Integer orderId, @RequestBody @Valid LocationDto destinationDto) {
        ordersService.updateDestination(orderId, destinationDto);
        return new ResponseEntity<>(MessageConstants.ORDER_DESTINATION_UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("id") Integer orderId) {
        ordersService.cancelOrder(orderId);
        return new ResponseEntity<>(MessageConstants.ORDER_CANCELLED_SUCCESSFULLY, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderDetails(@PathVariable("id") Integer orderId) {
        OrderDto userOrder = ordersService.getUserOrder(orderId);
        return ResponseEntity.ofNullable(userOrder);
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getUserOrders() {
        List<OrderDto> orders = ordersService.getUserOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
