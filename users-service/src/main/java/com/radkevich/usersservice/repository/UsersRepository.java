package com.radkevich.usersservice.repository;

import com.radkevich.usersservice.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<UsersEntity> findByUsername(String username);

}

