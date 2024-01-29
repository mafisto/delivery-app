package com.radkevich.adminservice.repository;

import com.radkevich.adminservice.entity.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface AdminsRepository extends JpaRepository<AdminsEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<AdminsEntity> findByUsername(String username);

}

