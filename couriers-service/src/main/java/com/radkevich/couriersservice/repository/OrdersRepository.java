package com.radkevich.couriersservice.repository;

import com.radkevich.couriersservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {



    @Transactional(readOnly = true)
    @Query("select o from OrdersEntity o join CouriersEntity c on o.courierId = c where c.id=:courierId")
    Optional<List<OrdersEntity>> getAllOrdersDetails(@Param("courierId") Integer courierId);
    @Transactional(readOnly = true)
    @Query("select o from OrdersEntity o join CouriersEntity c on o.courierId = c where c.id=:courierId and o.id =:orderId")
    Optional<OrdersEntity> getOrdersDetails(@Param("courierId") Integer courierId, @Param("orderId") Integer orderId);

}
