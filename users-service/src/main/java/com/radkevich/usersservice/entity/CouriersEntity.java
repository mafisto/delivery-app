package com.radkevich.usersservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "couriers", schema = "public", catalog = "farel")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class CouriersEntity extends PersonsEntity {

    @OneToOne
    @JoinColumn(name = "courier_status_id", referencedColumnName = "id", nullable = false)
    private CourierStatusesEntity courierStatusId;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private PersonsEntity personsById;


}
