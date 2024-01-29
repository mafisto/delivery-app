package com.radkevich.usersservice.repository;

import com.radkevich.usersservice.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
    @Transactional(readOnly = true)
    Optional<RolesEntity> findByRole(String role);
}
