package com.radkevich.authserver.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persons", schema = "public", catalog = "farel")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class PersonsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "mobile_number")
    private String mobileNumber;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RolesEntity roleId;

}
