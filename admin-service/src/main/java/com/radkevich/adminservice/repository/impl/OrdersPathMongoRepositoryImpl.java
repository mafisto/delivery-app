package com.radkevich.adminservice.repository.impl;

import com.radkevich.adminservice.entity.OrderPathEntity;
import com.radkevich.adminservice.repository.OrdersPathMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersPathMongoRepositoryImpl implements OrdersPathMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public OrderPathEntity getOrderPath(Integer orderId){
        Query query = new Query();
        query.addCriteria(Criteria.where("order_id").is(orderId));
        OrderPathEntity orderPathEntity = mongoTemplate.findOne(query, OrderPathEntity.class);
        return orderPathEntity;
    }

}
