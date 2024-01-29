package com.radkevich.adminservice.repository;

import com.radkevich.adminservice.entity.OrderLocationEntity;
import com.radkevich.adminservice.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderLocationRepository extends JpaRepository<OrderLocationEntity, Integer> {

}
