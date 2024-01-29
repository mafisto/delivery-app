package com.radkevich.usersservice.repository;

import com.radkevich.usersservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {



    @Transactional(readOnly = true)
    @Query("select o from OrdersEntity o join ParcelsEntity p on o.parcelId = p where p.userId.id=:userId")
    Optional<List<OrdersEntity>> getAllOrdersDetails(@Param("userId") Integer userId);
    @Transactional(readOnly = true)
    @Query("select o from OrdersEntity o join ParcelsEntity p on o.parcelId = p where o.id =:orderId and p.userId.id=:userId ")
    Optional<OrdersEntity> getOrdersDetails(@Param("userId") Integer userId, @Param("orderId") Integer orderId);

}
