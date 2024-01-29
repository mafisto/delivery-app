package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.CouriersEntity;
import com.radkevich.oderservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CouriersRepository  extends JpaRepository<CouriersEntity, Integer> {
    @Transactional(readOnly = true)
    Optional<CouriersEntity> findByUsername(String username);
}
