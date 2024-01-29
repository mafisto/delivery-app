package com.radkevich.couriersservice;

import com.radkevich.couriersservice.config.DockerContainers;
import com.radkevich.couriersservice.dto.ChangeOrderStatusDto;
import com.radkevich.couriersservice.dto.LocationDto;
import com.radkevich.couriersservice.dto.OrderDto;
import com.radkevich.couriersservice.dto.OrderStatuses;
import com.radkevich.couriersservice.exceptions.ChangeOrderStatusException;
import com.radkevich.couriersservice.exceptions.OrderNotFoundException;
import com.radkevich.couriersservice.services.OrdersService;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void updatelocation_SUCCESS_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(3));
        Integer orderId = 1;
        LocationDto location = new LocationDto();
        Assertions.assertDoesNotThrow(() -> ordersService.updateLocation(orderId, location));
    }

    @Test
    public void changeOrderStatus_SUCCESS_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(4));
        Integer orderId = 3;
        Assertions.assertDoesNotThrow(() -> ordersService.changeOrderStatus(orderId, new ChangeOrderStatusDto(OrderStatuses.DELIVERED)));
    }

    @Test
    public void changeOrderStatus_FAIL_worngUser_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(4));
        Integer orderId = 1;
        Assertions.assertThrows(OrderNotFoundException.class, () -> ordersService.changeOrderStatus(orderId, new ChangeOrderStatusDto(OrderStatuses.DELIVERED)));

    }

    @Test
    public void changeOrderStatus_FAIL_worngStatus_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(4));
        Integer orderId = 2;
        Assertions.assertThrows(ChangeOrderStatusException.class, () -> ordersService.changeOrderStatus(orderId, new ChangeOrderStatusDto(OrderStatuses.DELIVERED)));

    }

    @Test
    public void getUserOrder_SUCCESS_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(4));

        OrderDto order = new OrderDto();
        order.setId(3);
        order.setCreatedTime(new Timestamp(1706527800000L));
        order.setStartTime(new Timestamp(1706527860000L));
        order.setFinishTime(new Timestamp(1706528100000L));
        order.setCourierName("courier2");
        order.setCourierMobileNumber("52352");
        order.setStatus("In Transit");
        order.setStartLocation(new LocationDto(1.0, -12.0));
        order.setFinishLocation(new LocationDto(34.0522, -118.2437));
        order.setCurrentLocation(new LocationDto(32.0, -114.0));
        order.setParcelName("Food");

        OrderDto orderFromDb = ordersService.getOrder(order.getId());
        Assertions.assertEquals(order, orderFromDb);
    }

    @Test
    public void getUserOrder_FAIL_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(2));

        int orderIdNotBelongsToUser = 3;
        Assertions.assertThrows(OrderNotFoundException.class, () -> ordersService.getOrder(orderIdNotBelongsToUser));
    }


    @Test
    void getUsersOrders_wrongUser_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(2));
        List<OrderDto> userOrders = ordersService.getOrders();
        Assertions.assertEquals(userOrders.size(), 0);

    }

    @Test
    void getUserOrders_TEST() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(4));

        OrderDto order1 = new OrderDto();
        order1.setId(2);
        order1.setCreatedTime(new Timestamp(1706524200000L));
        order1.setCourierName("courier2");
        order1.setCourierMobileNumber("52352");
        order1.setStatus("Cancelled");
        order1.setParcelName("Clothing");

        OrderDto order2 = new OrderDto();
        order2.setId(3);
        order2.setCreatedTime(new Timestamp(1706527800000L));
        order2.setStartTime(new Timestamp(1706527860000L));
        order2.setFinishTime(new Timestamp(1706528100000L));
        order2.setCourierName("courier2");
        order2.setCourierMobileNumber("52352");
        order2.setStatus("In Transit");
        order2.setStartLocation(new LocationDto(1.0, -12.0));
        order2.setFinishLocation(new LocationDto(34.0522, -118.2437));
        order2.setCurrentLocation(new LocationDto(32.0, -114.0));
        order2.setParcelName("Food");

        OrderDto order3 = new OrderDto();
        order3.setId(4);
        order3.setCreatedTime(new Timestamp(1706527800000L));
        order3.setStartTime(new Timestamp(1706527860000L));
        order3.setFinishTime(new Timestamp(1706528100000L));
        order3.setCourierName("courier2");
        order3.setCourierMobileNumber("52352");
        order3.setStatus("In Transit");
        order3.setStartLocation(new LocationDto(1.0, -12.0));
        order3.setFinishLocation(new LocationDto(34.0522, -118.2437));
        order3.setCurrentLocation(new LocationDto(32.0, -114.0));
        order3.setParcelName("Food");


        List<OrderDto> userOrdersOrig = new ArrayList<>();
        userOrdersOrig.add(order1);
        userOrdersOrig.add(order2);
        userOrdersOrig.add(order3);

        List<OrderDto> userOrders = ordersService.getOrders();
        Assertions.assertEquals(userOrdersOrig, userOrders);
    }

}
