package com.radkevich.oderservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("order_path")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPathEntity {
    @Id
    private String id;

    @Field("order_id")
    private Integer orderId;

    @Field("coordinates")
    private List<List<Double>> coordinates;
}
