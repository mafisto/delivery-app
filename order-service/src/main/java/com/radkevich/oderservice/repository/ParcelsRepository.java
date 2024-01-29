package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.ParcelsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelsRepository extends JpaRepository<ParcelsEntity, Integer> {

}
