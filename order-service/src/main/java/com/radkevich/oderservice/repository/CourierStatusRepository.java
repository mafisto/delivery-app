package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.CourierStatusesEntity;
import com.radkevich.oderservice.entity.CouriersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierStatusRepository extends JpaRepository<CourierStatusesEntity, Integer> {
    @Query
    CourierStatusesEntity findByStatus(String status);
}
