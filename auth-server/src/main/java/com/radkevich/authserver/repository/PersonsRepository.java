package com.radkevich.authserver.repository;

import com.radkevich.authserver.repository.entity.PersonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonsRepository extends JpaRepository<PersonsEntity, UUID> {
    PersonsEntity findByEmail(String email);
    PersonsEntity findByUsername(String username);
}
