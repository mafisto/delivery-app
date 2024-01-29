package com.radkevich.oderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "orders", schema = "public", catalog = "farel")
@Getter
@Setter
@ToString
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "parcel_id", referencedColumnName = "id")
    private ParcelsEntity parcelId;
    @OneToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    private CouriersEntity courierId;
    @OneToOne
    @JoinColumn(name = "order_status_history_id", referencedColumnName = "id")
    private OrderStatusHistoryEntity orderStatusHistoryId;
    @OneToOne
    @JoinColumn(name = "order_location_id", referencedColumnName = "id")
    private OrderLocationEntity orderLocationId;
    @Column(name = "created_time")
    private Timestamp createdTime;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "finish_time")
    private Timestamp finishTime;

}
