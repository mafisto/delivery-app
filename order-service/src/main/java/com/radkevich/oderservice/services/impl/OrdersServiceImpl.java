package com.radkevich.oderservice.services.impl;

import com.radkevich.oderservice.dto.OrderCreateDto;
import com.radkevich.oderservice.dto.OrderStatuses;
import com.radkevich.oderservice.dto.PersonRoles;
import com.radkevich.oderservice.dto.broker.AssignCourierToOrderMessage;
import com.radkevich.oderservice.dto.broker.CancelOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeDestinationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeLocationOrderMessage;
import com.radkevich.oderservice.dto.broker.ChangeOrderStatusMessage;
import com.radkevich.oderservice.dto.broker.CreateOrderMessage;
import com.radkevich.oderservice.entity.CouriersEntity;
import com.radkevich.oderservice.entity.OrderLocationEntity;
import com.radkevich.oderservice.entity.OrderStatusHistoryEntity;
import com.radkevich.oderservice.entity.OrderStatusesEntity;
import com.radkevich.oderservice.entity.OrdersEntity;
import com.radkevich.oderservice.entity.ParcelsEntity;
import com.radkevich.oderservice.entity.PersonsEntity;
import com.radkevich.oderservice.entity.UsersEntity;
import com.radkevich.oderservice.exceptions.AssignCourierToOrderException;
import com.radkevich.oderservice.exceptions.ChangeOrderCoordinatesException;
import com.radkevich.oderservice.exceptions.ChangeOrderStatusException;
import com.radkevich.oderservice.exceptions.CreateOrderException;
import com.radkevich.oderservice.exceptions.InsufficientRightsException;
import com.radkevich.oderservice.exceptions.OrderNotFoundException;
import com.radkevich.oderservice.repository.CouriersRepository;
import com.radkevich.oderservice.repository.OrderLocationRepository;
import com.radkevich.oderservice.repository.OrderStatusHistoryRepository;
import com.radkevich.oderservice.repository.OrderStatusesRepository;
import com.radkevich.oderservice.repository.OrdersPathRepository;
import com.radkevich.oderservice.repository.OrdersRepository;
import com.radkevich.oderservice.repository.ParcelsRepository;
import com.radkevich.oderservice.repository.PersonsRepository;
import com.radkevich.oderservice.repository.UsersRepository;
import com.radkevich.oderservice.services.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.SQLGrammarException;
import org.postgresql.geometric.PGpoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final PersonsRepository personsRepository;
    private final UsersRepository usersRepository;
    private final OrderLocationRepository orderLocationRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderStatusesRepository orderStatusesRepository;
    private final CouriersRepository couriersRepository;
    private final ParcelsRepository parcelsRepository;
    private final OrdersPathRepository orderPathRepository;


    @Override
    @Transactional
    public OrdersEntity createOrder(CreateOrderMessage data) {
        try {
            OrderCreateDto dto = data.getData();
            OrdersEntity entity = new OrdersEntity();

            // create parcel
            ParcelsEntity parcelsEntity = new ParcelsEntity();
            parcelsEntity.setItemName(dto.getItemName());
            UsersEntity user = usersRepository.findById(data.getUserId()).get();
            parcelsEntity.setUserId(user);

            //create location entity
            OrderLocationEntity orderLocationEntity = new OrderLocationEntity();
            orderLocationEntity.setFinishLocation(new PGpoint(dto.getDestination().getX(), dto.getDestination().getY()));
            orderLocationEntity.setStartLocation(new PGpoint(dto.getStartLocation().getX(),dto.getStartLocation().getY()));
            orderLocationEntity.setCurrentLocation(new PGpoint(dto.getStartLocation().getX(), dto.getStartLocation().getY()));

            // create order status history
            OrderStatusHistoryEntity orderStatusHistory = new OrderStatusHistoryEntity();
            orderStatusHistory.setStatusId(orderStatusesRepository.findByStatus(OrderStatuses.CREATED.getValue()).get());
            orderStatusHistory.setChangeTime(Timestamp.from(Instant.now()));
            orderStatusHistory.setAgentId(user);


            ParcelsEntity p = parcelsRepository.save(parcelsEntity);
            OrderLocationEntity ol = orderLocationRepository.save(orderLocationEntity);
            OrderStatusHistoryEntity osh = orderStatusHistoryRepository.save(orderStatusHistory);

            entity.setParcelId(p);
            entity.setOrderLocationId(ol);
            entity.setOrderStatusHistoryId(osh);
            entity.setCreatedTime(Timestamp.from(Instant.now()));

            OrdersEntity order = ordersRepository.save(entity);
            return order;
        } catch (Exception ex) {
            throw new CreateOrderException(ex);
        }
    }

    @Override
    @Transactional
    public void changeOrderDestination(ChangeDestinationOrderMessage data) {
        try {
            Optional<OrdersEntity> order = ordersRepository.findById(data.getOrderId());
            if(order.isPresent()){
                PGpoint point = new PGpoint(data.getData().getX(), data.getData().getY());
                OrderLocationEntity location = order.get().getOrderLocationId();
                if(location == null){
                    location = new OrderLocationEntity();
                    location.setFinishLocation(point);
                } else {
                    location.setFinishLocation(point);
                }
                orderLocationRepository.save(location);
            } else {
                throw new OrderNotFoundException(data.getOrderId());
            }
        } catch (SQLGrammarException | NullPointerException ex) {
            throw new ChangeOrderCoordinatesException(ex);
        }
    }

    @Override
    @Transactional
    public void cancelOrder(CancelOrderMessage data) {
        try {
            OrderStatusesEntity status = orderStatusesRepository.findByStatus(OrderStatuses.CANCELLED.getValue()).get();
            PersonsEntity user = personsRepository.findById(data.getUserId()).get();
            OrdersEntity order = ordersRepository.findById(data.getOrderId()).get();
            if (!user.equals(order.getParcelId().getUserId())) {
                throw new ChangeOrderStatusException();
            }
            order.setFinishTime(Timestamp.from(Instant.now()));
            order.getOrderStatusHistoryId().setAgentId(user);
            order.getOrderStatusHistoryId().setChangeTime(Timestamp.from(Instant.now()));
            order.getOrderStatusHistoryId().setStatusId(status);
            ordersRepository.save(order);
        } catch (NoSuchElementException ex) {
            throw new ChangeOrderStatusException(ex);
        }
    }


    @Override
    @Transactional
    public void changeOrderStatus(ChangeOrderStatusMessage data) {
        try {
            OrderStatusesEntity status = orderStatusesRepository.findByStatus(data.getStatus()).get();
            PersonsEntity person = personsRepository.findById(data.getPersonId()).get();
            OrdersEntity order = ordersRepository.findById(data.getOrderId()).get();
            if (person.getRoleId().getRole().equals(PersonRoles.USER.getValue())) {
                throw new InsufficientRightsException(PersonRoles.ADMIN.getValue(), PersonRoles.COURIER.getValue());
            }
            if (data.getStatus().equals(OrderStatuses.PROCESSING.getValue())) {
                order.setStartTime(Timestamp.from(Instant.now()));
            }
            if (data.getStatus().equals(OrderStatuses.DELIVERED.getValue())) {
                order.setFinishTime(Timestamp.from(Instant.now()));
            }
            order.getOrderStatusHistoryId().setAgentId(person);
            order.getOrderStatusHistoryId().setChangeTime(Timestamp.from(Instant.now()));
            order.getOrderStatusHistoryId().setStatusId(status);
            ordersRepository.save(order);
        } catch (NoSuchElementException ex) {
            throw new ChangeOrderStatusException(ex);
        }
    }

    @Override
    @Transactional
    public void changeOrderCoordinates(ChangeLocationOrderMessage data) {
        try {
            PGpoint point = new PGpoint(data.getData().getX(), data.getData().getY());
            orderLocationRepository.updateOrderLocation(data.getOrderId(), point);
            orderPathRepository.updateOrderPath(data.getOrderId(), point);
        } catch (SQLGrammarException | NullPointerException ex) {
            throw new ChangeOrderCoordinatesException(ex);
        }
    }

    @Override
    @Transactional
    public void assignCourierToOrder(AssignCourierToOrderMessage data) {
        try {
            PersonsEntity personsEntity = personsRepository.findById(data.getAgentId()).get();
            String role = personsEntity.getRoleId().getRole();
            boolean isAllowedRole = role.equals(PersonRoles.ADMIN.getValue());
            if (!isAllowedRole) {
                throw new InsufficientRightsException(PersonRoles.ADMIN.getValue(), PersonRoles.COURIER.getValue());
            }
            OrdersEntity ordersEntity = ordersRepository.findById(data.getOrderId()).get();
            CouriersEntity couriersEntity = couriersRepository.findById(data.getCourierId()).get();
            ordersEntity.setCourierId(couriersEntity);
            ordersRepository.save(ordersEntity);
        } catch (NoSuchElementException ex) {
            throw new AssignCourierToOrderException(ex);
        }

    }
}
