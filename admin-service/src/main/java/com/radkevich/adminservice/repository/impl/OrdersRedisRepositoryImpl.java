package com.radkevich.adminservice.repository.impl;

import com.radkevich.adminservice.dto.OrderDto;
import com.radkevich.adminservice.dto.OrdersListDto;
import com.radkevich.adminservice.repository.OrdersRedisRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrdersRedisRepositoryImpl implements OrdersRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Integer, OrderDto> hashOps;
    private ValueOperations<String, Object> valueOps;

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
        valueOps = redisTemplate.opsForValue();
    }

    @Override
    public boolean hasCachedOrder(Integer orderId) {
        return hashOps.hasKey(ORDER_KEY, orderId);
    }

    @Override
    public void put(Integer orderId, OrderDto dto) {
        hashOps.put(ORDER_KEY, orderId, dto);
        redisTemplate.expire(ORDER_KEY, 15, TimeUnit.SECONDS);

    }

    @Override
    public OrderDto get(Integer orderId) {
        return hashOps.get(ORDER_KEY, orderId);
    }

    @Override
    public List<OrderDto> getOrders() {
        OrdersListDto ordersListDto = (OrdersListDto) valueOps.get(ORDERS_LIST_KEY);
        if (ordersListDto != null) {
            return ordersListDto.getOrders();
        } else return Collections.emptyList();
    }

    @Override
    public void putOrders(List<OrderDto> orders) {
        OrdersListDto ordersListDto = new OrdersListDto(orders);
        valueOps.set(ORDERS_LIST_KEY, ordersListDto);
        redisTemplate.expire(ORDERS_LIST_KEY, 15, TimeUnit.SECONDS);
    }
}
