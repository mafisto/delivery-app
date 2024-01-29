package com.radkevich.couriersservice.entity;

import com.radkevich.couriersservice.serializers.PGPointType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpoint;

@Entity
@Table(name = "order_location", schema = "public", catalog = "farel")
@Getter
@Setter
public class OrderLocationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Type(value = PGPointType.class)
    @Column(name = "start_location", columnDefinition = "point")
    private PGpoint startLocation;

    @Type(value = PGPointType.class)
    @Column(name = "finish_location", columnDefinition = "point")
    private PGpoint finishLocation;
    @Type(value = PGPointType.class)
    @Column(name = "current_location", columnDefinition = "point")
    private PGpoint currentLocation;



}
