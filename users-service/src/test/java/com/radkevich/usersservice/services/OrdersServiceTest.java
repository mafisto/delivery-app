package com.radkevich.usersservice.services;

import com.radkevich.usersservice.TestSecurityHelper;
import com.radkevich.usersservice.config.DockerContainers;
import com.radkevich.usersservice.dto.LocationDto;
import com.radkevich.usersservice.dto.OrderCreateDto;
import com.radkevich.usersservice.dto.OrderDto;
import com.radkevich.usersservice.dto.OrderStatuses;
import com.radkevich.usersservice.exception.CancelOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class OrdersServiceTest extends DockerContainers {
    @Autowired
    private OrdersService ordersService;


    @BeforeEach
    public void before() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(2));
    }


    @Test
    void getUserOrders() {
        OrderDto order1 = new OrderDto();
        order1.setId(1);
        order1.setCreatedTime(new Timestamp(1706518800000L));
        order1.setCourierName("courier1");
        order1.setCourierMobileNumber("5555555555");
        order1.setStatus("Created");
        order1.setStartLocation(new LocationDto(40.7128, -74.006));
        order1.setFinishLocation(new LocationDto(41.8781, -87.6298));
        order1.setCurrentLocation(new LocationDto(40.7128, -74.006));
        order1.setParcelName("Electronics");

        OrderDto order2 = new OrderDto();
        order2.setId(2);
        order2.setCreatedTime(new Timestamp(1706524200000L));
        order2.setCourierName("courier2");
        order2.setCourierMobileNumber("52352");
        order2.setStatus("Processing");
        order2.setParcelName("Clothing");


        OrderDto order3 = new OrderDto();
        order3.setId(3);
        order3.setCreatedTime(new Timestamp(1706527800000L));
        order3.setFinishTime(new Timestamp(1706528100000L));
        order3.setStartTime(new Timestamp(1706527860000L));
        order3.setCourierName("courier2");
        order3.setCourierMobileNumber("52352");
        order3.setStatus("In Transit");
        order3.setStartLocation(new LocationDto(1.0, -12.0));
        order3.setFinishLocation(new LocationDto(34.0522, -118.2437));
        order3.setCurrentLocation(new LocationDto(32.0, -114.0));
        order3.setParcelName("Food");

        OrderDto order4 = new OrderDto();
        order4.setId(4);
        order4.setCreatedTime(new Timestamp(1706527800000L));
        order4.setFinishTime(new Timestamp(1706528100000L));
        order4.setStartTime(new Timestamp(1706527860000L));
        order4.setCourierName("courier2");
        order4.setCourierMobileNumber("52352");
        order4.setStatus("In Transit");
        order4.setStartLocation(new LocationDto(1.0, -12.0));
        order4.setFinishLocation(new LocationDto(34.0522, -118.2437));
        order4.setCurrentLocation(new LocationDto(32.0, -114.0));
        order4.setParcelName("Food");

        List<OrderDto> userOrdersOrig = new ArrayList<>();
        userOrdersOrig.add(order1);
        userOrdersOrig.add(order2);
        userOrdersOrig.add(order3);
        userOrdersOrig.add(order4);

        List<OrderDto> userOrders = ordersService.getUserOrders();
        Assertions.assertEquals(userOrdersOrig, userOrders);
    }

    @Test
    public void getUserOrder_test() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setCreatedTime(new Timestamp(1706518800000L));
        orderDto.setCourierName("courier1");
        orderDto.setCourierMobileNumber("5555555555");
        orderDto.setStatus(OrderStatuses.CREATED.getValue());
        orderDto.setStartLocation(new LocationDto(40.7128, -74.006));
        orderDto.setFinishLocation(new LocationDto(41.8781, -87.6298));
        orderDto.setCurrentLocation(new LocationDto(40.7128, -74.006));
        orderDto.setParcelName("Electronics");

        Integer orderId = 1;
        OrderDto orderFromDb = ordersService.getUserOrder(orderId);
        Assertions.assertEquals(orderDto, orderFromDb);

    }

    @Test
    public void updateDestination_test() {
        Integer orderId = 1;
        LocationDto destination = new LocationDto();
        Assertions.assertDoesNotThrow(() -> ordersService.updateDestination(orderId, destination));
    }

    @Test
    public void cancelOrder_SUCCESS_test() {
        Integer orderId = 1;
        Assertions.assertDoesNotThrow(() -> ordersService.cancelOrder(orderId));
    }

    @Test
    public void cancelOrder_FAIL_test() {
        Integer orderId = 2;
        Assertions.assertThrows(CancelOrderException.class, () -> ordersService.cancelOrder(orderId));

    }

    @Test
    public void createOrder_test() {
        OrderCreateDto order = new OrderCreateDto();
        Assertions.assertDoesNotThrow(() -> ordersService.createOrder(order));
    }
}
