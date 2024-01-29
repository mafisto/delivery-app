package com.radkevich.couriersservice.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins", schema = "public", catalog = "farel")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class AdminsEntity extends PersonsEntity {
    @Basic
    @Column(name = "superadmin")
    private Boolean superadmin;

}
