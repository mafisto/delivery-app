package com.radkevich.adminservice.services;


import com.radkevich.adminservice.TestSecurityHelper;
import com.radkevich.adminservice.config.DockerContainers;
import com.radkevich.adminservice.dto.AssignCourierToOrderDto;
import com.radkevich.adminservice.dto.ChangeOrderStatusDto;
import com.radkevich.adminservice.dto.LocationDto;
import com.radkevich.adminservice.dto.OrderDto;
import com.radkevich.adminservice.dto.OrderLocationDto;
import com.radkevich.adminservice.dto.OrderPathDto;
import com.radkevich.adminservice.dto.OrderStatuses;
import com.radkevich.adminservice.exceptions.AssignCourierToOrderException;
import com.radkevich.adminservice.exceptions.OrderNotFoundException;
import com.radkevich.adminservice.repository.OrdersRedisRepository;
import com.radkevich.adminservice.repository.OrdersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class OrdersServiceTest extends DockerContainers {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersRepository ordersRepository;

    @Mock
    OrdersRedisRepository ordersRedisRepository;

    @Test
    public void getOrder_SUCCESS_test() {
        Integer orderId = 3;
        LocationDto currentLocation = new LocationDto();
        currentLocation.setX(32.0);
        currentLocation.setY(-114.0);

        LocationDto finishLocation = new LocationDto();
        finishLocation.setX(34.0522);
        finishLocation.setY(-118.2437);

        LocationDto startLocation = new LocationDto();
        startLocation.setX(1.0);
        startLocation.setY(-12.0);

        OrderDto orderOrig = new OrderDto();
        orderOrig.setId(3);
        orderOrig.setCreatedTime(new Timestamp(1706527800000L));
        orderOrig.setStartTime(new Timestamp(1706527860000L));
        orderOrig.setFinishTime(new Timestamp(1706528100000L));
        orderOrig.setCourierName("courier2");
        orderOrig.setCourierMobileNumber("52352");
        orderOrig.setStatus("In Transit");
        orderOrig.setStartLocation(startLocation);
        orderOrig.setFinishLocation(finishLocation);
        orderOrig.setCurrentLocation(currentLocation);
        orderOrig.setParcelName("Food");


        when(ordersRedisRepository.get(orderId)).thenReturn(null);
        OrderDto orderDb = ordersService.getOrder(orderId);

        Assertions.assertEquals(orderOrig, orderDb);
    }

    @Test
    public void getOrder_FAIL_test() {
        Integer orderId = 666;
        doNothing().when(ordersRedisRepository).put(orderId, null);
        when(ordersRedisRepository.get(orderId)).thenReturn(null);
        Assertions.assertThrows(OrderNotFoundException.class, () -> ordersService.getOrder(orderId));
    }

    @Test
    public void getOrders_SUCCESS_test() {
        OrderDto order = new OrderDto();
        order.setId(1);
        order.setCreatedTime(new Timestamp(1706518800000L));
        order.setCourierName("courier1");
        order.setCourierMobileNumber("5555555555");
        order.setStatus(OrderStatuses.CREATED.getValue());
        order.setStartLocation(new LocationDto(40.7128, -74.006));
        order.setFinishLocation(new LocationDto(41.8781, -87.6298));
        order.setCurrentLocation(new LocationDto(40.7128, -74.006));
        order.setParcelName("Electronics");

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


        List<OrderDto> ordersOrig = new ArrayList<>();
        ordersOrig.add(order);
        ordersOrig.add(order1);
        ordersOrig.add(order2);
        ordersOrig.add(order3);

        List<OrderDto> ordersDb = ordersService.getOrders();
        Assertions.assertFalse(ordersDb.isEmpty());
        Assertions.assertEquals(ordersOrig, ordersDb);

    }

    @Test
    public void changeOrderStatus_SUCCESS_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(1));
        Integer orderId = 3;
        ChangeOrderStatusDto dto = new ChangeOrderStatusDto(OrderStatuses.DELIVERED);
        Assertions.assertDoesNotThrow(() -> ordersService.changeOrderStatus(orderId, dto));

    }

    @Test
    public void changeOrderStatus_FAIL_test() {
        Integer orderId = 666;
        ChangeOrderStatusDto dto = new ChangeOrderStatusDto(OrderStatuses.DELIVERED);
        Assertions.assertThrows(OrderNotFoundException.class, () -> ordersService.changeOrderStatus(orderId, dto));
    }

    @Test
    public void assignCourierToOrder_SUCCESS_test() {
        SecurityContextHolder.getContext().setAuthentication(TestSecurityHelper.getTestAuthentication(1));

        Integer orderId = 4;
        AssignCourierToOrderDto orderDto = new AssignCourierToOrderDto(4);
        Assertions.assertDoesNotThrow(() -> ordersService.assignCourierToOrder(orderId, orderDto));
    }

    @Test
    public void assignCourierToOrder_ORDER_NOT_EXISTS_FAIL_test() {
        Integer orderId = 14;
        AssignCourierToOrderDto orderDto = new AssignCourierToOrderDto(4);
        Assertions.assertThrows(AssignCourierToOrderException.class, () -> ordersService.assignCourierToOrder(orderId, orderDto));
    }

    @Test
    public void assignCourierToOrder_COURIER_NOT_EXISTS_FAIL_test() {
        Integer orderId = 4;
        AssignCourierToOrderDto orderDto = new AssignCourierToOrderDto(444);
        Assertions.assertThrows(AssignCourierToOrderException.class, () -> ordersService.assignCourierToOrder(orderId, orderDto));
    }

    @Test
    public void getOrderLocation_SUCCESS_test() {
        Integer orderId = 3;
        LocationDto currentLocation = new LocationDto();
        currentLocation.setX(32.0);
        currentLocation.setY(-114.0);

        LocationDto finishLocation = new LocationDto();
        finishLocation.setX(34.0522);
        finishLocation.setY(-118.2437);

        LocationDto startLocation = new LocationDto();
        startLocation.setX(1.0);
        startLocation.setY(-12.0);

        OrderLocationDto locationOrig = new OrderLocationDto();
        locationOrig.setId(3);
        locationOrig.setStartLocation(startLocation);
        locationOrig.setFinishLocation(finishLocation);
        locationOrig.setCurrentLocation(currentLocation);


        OrderLocationDto locationDb = ordersService.getOrderLocation(orderId);

        Assertions.assertEquals(locationOrig, locationDb);

    }

    @Test
    public void getOrderLocation_FAIL_test() {
        Integer orderId = 666;
        Assertions.assertThrows(OrderNotFoundException.class, () -> ordersService.getOrderLocation(orderId));
    }

    @Test
    public void  getOrderPath_SUCCESS_TEST(){
        Integer orderId=1;
        List<List<Double>> coordinates = List.of(
                List.of(1.0, 11.0),
                List.of(2.0, 22.0),
                List.of(3.0, 33.0),
                List.of(4.0, 44.0),
                List.of(5.0, 55.0),
                List.of(6.0, 66.0),
                List.of(7.0, 77.0),
                List.of(8.0, 88.0),
                List.of(9.0, 99.0),
                List.of(7.0, 222.0)
        );
        OrderPathDto orderPath = new OrderPathDto();
        orderPath.setOrderId(1);
        orderPath.setCoordinates(coordinates);
        OrderPathDto orderPathDb = ordersService.getOrderPath(orderId);
        Assertions.assertEquals(orderPath, orderPathDb);
    }
    @Test
    public void  getOrderPath_FAIL_TEST(){
        Integer orderId=666;
        OrderPathDto orderPathDb = ordersService.getOrderPath(orderId);
        Assertions.assertNull(orderPathDb);
    }
}
