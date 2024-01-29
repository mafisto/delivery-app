package com.radkevich.adminservice.controllers;


import com.radkevich.adminservice.dto.AssignCourierToOrderDto;
import com.radkevich.adminservice.dto.ChangeOrderStatusDto;
import com.radkevich.adminservice.dto.OrderDto;
import com.radkevich.adminservice.dto.OrderLocationDto;
import com.radkevich.adminservice.dto.OrderPathDto;
import com.radkevich.adminservice.services.OrdersService;
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

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("id") Integer orderId, @RequestBody @Valid ChangeOrderStatusDto dto) {
        ordersService.changeOrderStatus(orderId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<String> assignCourier(@PathVariable("id") Integer orderId, @RequestBody @Valid AssignCourierToOrderDto dto) {
        ordersService.assignCourierToOrder(orderId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/track")
    public ResponseEntity<OrderLocationDto> getOrderLocation(@PathVariable("id") Integer orderId) {
        OrderLocationDto orderLocation = ordersService.getOrderLocation(orderId);
        return new ResponseEntity<>(orderLocation, HttpStatus.OK);
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<OrderPathDto> getOrderPath(@PathVariable("id") Integer orderId) {
        OrderPathDto orderPath = ordersService.getOrderPath(orderId);
        return new ResponseEntity<>(orderPath, HttpStatus.OK);
    }


}