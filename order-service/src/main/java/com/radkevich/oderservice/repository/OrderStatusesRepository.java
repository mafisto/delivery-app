package com.radkevich.oderservice.repository;


import com.radkevich.oderservice.entity.OrderStatusHistoryEntity;
import com.radkevich.oderservice.entity.OrderStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusesRepository extends JpaRepository<OrderStatusesEntity, Integer> {
    Optional<OrderStatusesEntity> findByStatus(String value);
}
