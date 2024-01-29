package com.radkevich.couriersservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", schema = "public", catalog = "farel")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class UsersEntity extends PersonsEntity {

    @Column(name = "vip")
    private Boolean vip;


}
