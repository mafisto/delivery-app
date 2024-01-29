package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.PersonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PersonsRepository extends JpaRepository<PersonsEntity, Integer> {


}

