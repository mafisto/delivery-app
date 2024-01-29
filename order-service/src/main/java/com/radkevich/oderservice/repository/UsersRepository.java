package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.PersonsEntity;
import com.radkevich.oderservice.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {


}

