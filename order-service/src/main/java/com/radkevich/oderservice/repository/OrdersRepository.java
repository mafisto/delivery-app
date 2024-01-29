package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {

}
