package com.radkevich.usersservice.dto;

import com.radkevich.usersservice.entity.OrderLocationEntity;
import com.radkevich.usersservice.entity.OrderStatusHistoryEntity;
import com.radkevich.usersservice.entity.OrdersEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetails {
    OrderLocationEntity ol;

    OrderStatusHistoryEntity osh;

    OrdersEntity o;
}
