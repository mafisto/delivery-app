package com.radkevich.adminservice.entity;

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

import java.sql.Timestamp;

@Entity
@Table(name = "order_status_history", schema = "public", catalog = "farel")
@Getter
@Setter
public class OrderStatusHistoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "agent_id", referencedColumnName = "id")
    private PersonsEntity agentId;
    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatusesEntity statusId;
    @Column(name = "change_time")
    private Timestamp changeTime;


}
