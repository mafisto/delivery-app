package com.radkevich.oderservice.repository;


import com.radkevich.oderservice.entity.OrderStatusHistoryEntity;
import com.radkevich.oderservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryEntity, Integer> {
}
