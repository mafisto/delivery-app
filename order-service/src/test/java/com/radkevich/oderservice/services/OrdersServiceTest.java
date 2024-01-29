package com.radkevich.oderservice.services;


import com.radkevich.oderservice.config.DockerContainers;
import com.radkevich.oderservice.dto.LocationDto;
import com.radkevich.oderservice.dto.OrderCreateDto;
import com.radkevich.oderservice.dto.OrderStatuses;
import com.radkevich.oderservice.dto.broker.AssignCourierToOrderMessage;
import com.radkevich.oderservice.dto.broker.CancelOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeDestinationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeLocationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeOrderStatusMessage;
import com.radkevich.oderservice.dto.broker.CreateOrderMessage;
import com.radkevich.oderservice.entity.OrdersEntity;
import com.radkevich.oderservice.exceptions.ChangeOrderCoordinatesException;
import com.radkevich.oderservice.exceptions.ChangeOrderStatusException;
import com.radkevich.oderservice.exceptions.CreateOrderException;
import com.radkevich.oderservice.exceptions.InsufficientRightsException;
import com.radkevich.oderservice.exceptions.OrderNotFoundException;
import com.radkevich.oderservice.repository.OrdersRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class OrdersServiceTest extends DockerContainers {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersRepository ordersRepository;

    private final Integer adminId = 1;
    private final Integer userId = 2;
    private final Integer courierId = 3;
    private final Integer orderId = 1;
    private final Integer wrongOrderId = 1111;
    private final Integer wrongUser = 222;

    @Test
    public void createOrder_SUCCESS_test() {
        CreateOrderMessage msg = CreateOrderMessage.builder().data(OrderCreateDto.builder()
                        .destination(new LocationDto(222, 222))
                        .startLocation(new LocationDto(111, 111))
                        .itemName("test-item")
                        .build())
                .userId(userId)
                .build();
        OrdersEntity order = ordersService.createOrder(msg);


        Assertions.assertEquals(msg.getData().getItemName(), order.getParcelId().getItemName());
        Assertions.assertEquals(msg.getUserId(), order.getParcelId().getUserId().getId());
        Assertions.assertEquals(msg.getData().getStartLocation().getX(), order.getOrderLocationId().getStartLocation().x);
        Assertions.assertEquals(msg.getData().getStartLocation().getY(), order.getOrderLocationId().getStartLocation().y);
        Assertions.assertEquals(msg.getData().getDestination().getX(), order.getOrderLocationId().getFinishLocation().x);
        Assertions.assertEquals(msg.getData().getDestination().getY(), order.getOrderLocationId().getFinishLocation().y);
        Assertions.assertEquals(OrderStatuses.CREATED.getValue(), order.getOrderStatusHistoryId().getStatusId().getStatus());
        Assertions.assertEquals(msg.getUserId(), order.getOrderStatusHistoryId().getAgentId().getId());


    }

    @Test
    public void createOrder_FAIL_test() {
        CreateOrderMessage msg = CreateOrderMessage.builder().data(OrderCreateDto.builder()
                        .destination(new LocationDto(222, 222))
                        .startLocation(new LocationDto(111, 111))
                        .itemName("test-item")
                        .build())
                .userId(wrongUser)
                .build();

        Assertions.assertThrows(CreateOrderException.class, () -> ordersService.createOrder(msg));
    }


    @Test
    public void changeOrderDestination_SUCCESS_test() {
        ChangeDestinationOrderMessage msg = ChangeDestinationOrderMessage.builder()
                .orderId(orderId)
                .data(new LocationDto(111, 222))
                .build();

        ordersService.changeOrderDestination(msg);
        Optional<OrdersEntity> order = ordersRepository.findById(msg.getOrderId());
        Assertions.assertTrue(order.isPresent());
        PGpoint finishLocation = ordersRepository.findById(msg.getOrderId()).get().getOrderLocationId().getFinishLocation();

        Assertions.assertEquals(new PGpoint(msg.getData().getX(), msg.getData().getY()), finishLocation);
    }

    @Test
    public void changeOrderDestination_FAIL_test() {
        ChangeDestinationOrderMessage msg = ChangeDestinationOrderMessage.builder()
                .orderId(wrongOrderId)
                .data(null)
                .build();
        Assertions.assertThrows(OrderNotFoundException.class, () -> ordersService.changeOrderDestination(msg));
    }

    @Test
    public void cancelOrder_SUCCESS_test() {
        CancelOrderMessage msg = CancelOrderMessage.builder()
                .orderId(orderId).userId(userId)
                .build();
        ordersService.cancelOrder(msg);
        Optional<OrdersEntity> order = ordersRepository.findById(msg.getOrderId());
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(OrderStatuses.CANCELLED.getValue(), order.get().getOrderStatusHistoryId().getStatusId().getStatus());
    }

    @Test
    public void cancelOrder_FAIL_test() {
        CancelOrderMessage msg = CancelOrderMessage.builder()
                .orderId(orderId).userId(courierId)
                .build();
        Assertions.assertThrows(ChangeOrderStatusException.class, () -> ordersService.cancelOrder(msg));
    }

    @Test
    public void changeOrderStatus_SUCCESS_test() {
        ChangeOrderStatusMessage msg = ChangeOrderStatusMessage.builder()
                .status(OrderStatuses.IN_TRANSIT.getValue())
                .personId(adminId).orderId(orderId).build();
        ordersService.changeOrderStatus(msg);

        Optional<OrdersEntity> order = ordersRepository.findById(msg.getOrderId());
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(msg.getStatus(), order.get().getOrderStatusHistoryId().getStatusId().getStatus());

    }

    @Test
    public void changeOrderStatus_FAIL_test() {
        ChangeOrderStatusMessage msg = ChangeOrderStatusMessage.builder()
                .status(OrderStatuses.IN_TRANSIT.getValue())
                .personId(userId).orderId(orderId).build();

        Assertions.assertThrows(InsufficientRightsException.class, () -> ordersService.changeOrderStatus(msg));
    }

    @Test
    @Ignore("something wrong with connection to mongodb. in debug mode this test works fine")
    public void changeOrderCoordinates_SUCCESS_test() {
        ChangeLocationOrderMessage msg = ChangeLocationOrderMessage.builder()
                .data(new LocationDto(111, 222))
                .orderId(orderId).build();


        ordersService.changeOrderCoordinates(msg);
        Optional<OrdersEntity> order = ordersRepository.findById(msg.getOrderId());
        Assertions.assertTrue(order.isPresent());
        PGpoint currentLocation = ordersRepository.findById(msg.getOrderId()).get().getOrderLocationId().getCurrentLocation();

        Assertions.assertEquals(new PGpoint(msg.getData().getX(), msg.getData().getY()), currentLocation);
    }

    @Test
    public void changeOrderCoordinates_FAIL_test() {
        ChangeLocationOrderMessage msg = ChangeLocationOrderMessage.builder()
                .data(null)
                .orderId(wrongOrderId).build();
        Assertions.assertThrows(ChangeOrderCoordinatesException.class, () -> ordersService.changeOrderCoordinates(msg));
    }


    @Test
    public void assignCourierToOrder_ADMIN_SUCCESS_test() {
        AssignCourierToOrderMessage msg = AssignCourierToOrderMessage.builder()
                .orderId(orderId).agentId(adminId).courierId(courierId).build();
        ordersService.assignCourierToOrder(msg);
        Optional<OrdersEntity> order = ordersRepository.findById(msg.getOrderId());
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(msg.getCourierId(), order.get().getCourierId().getId());
    }

    @Test
    public void assignCourierToOrder_COURIER_FAIL_test() {
        AssignCourierToOrderMessage msg = AssignCourierToOrderMessage.builder()
                .orderId(orderId).agentId(courierId).courierId(courierId).build();
        Assertions.assertThrows(InsufficientRightsException.class, () -> ordersService.assignCourierToOrder(msg));
    }

    @Test
    public void assignCourierToOrder_USER_FAIL_test() {
        AssignCourierToOrderMessage msg = AssignCourierToOrderMessage.builder()
                .orderId(orderId).agentId(userId).courierId(courierId).build();
        Assertions.assertThrows(InsufficientRightsException.class, () -> ordersService.assignCourierToOrder(msg));
    }

}
