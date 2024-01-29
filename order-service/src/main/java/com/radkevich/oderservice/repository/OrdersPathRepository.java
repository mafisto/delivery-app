package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.dto.OrderPathDto;
import com.radkevich.oderservice.entity.OrderPathEntity;
import lombok.RequiredArgsConstructor;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersPathRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * Получение всех координат следования заказа
     * @param orderId идентификатор заказа
     * @return данные о координатах следования
     */
    public OrderPathEntity getOrderPath(Integer orderId){
        Query query = new Query();
        query.addCriteria(Criteria.where("order_id").is(orderId));
        OrderPathEntity orderPathEntity = mongoTemplate.findOne(query, OrderPathEntity.class);
        return orderPathEntity;
    }

    public void updateOrderPath(Integer orderId, PGpoint point){
        Query query = new Query(Criteria.where("order_id").is(orderId));
        Update update = new Update().push("coordinates", List.of(point.x, point.y));
        FindAndModifyOptions options = new FindAndModifyOptions().upsert(true).returnNew(true);

        OrderPathEntity updatedOrderPath = mongoTemplate.findAndModify(query, update, options, OrderPathEntity.class);
    }

}
