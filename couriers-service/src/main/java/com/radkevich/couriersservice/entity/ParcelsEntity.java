package com.radkevich.couriersservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parcels", schema = "public", catalog = "farel")
@Getter
@Setter
public class ParcelsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UsersEntity userId;
    @Column(name = "item_name")
    private String itemName;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private UsersEntity usersByUserId;


}
