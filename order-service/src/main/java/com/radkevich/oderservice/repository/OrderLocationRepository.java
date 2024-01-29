package com.radkevich.oderservice.repository;

import com.radkevich.oderservice.entity.OrderLocationEntity;
import org.postgresql.geometric.PGpoint;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLocationRepository extends JpaRepository<OrderLocationEntity, Integer> {

    @Modifying
    @Query(value = "update order_location  set finish_location = point(:x,:y) from orders o where order_location.id=o.order_location_id and o.id=:orderId", nativeQuery = true)
    void updateOrderDestinationOLD(@Param(value = "orderId") Integer orderId, @Param(value = "x") double x, @Param(value = "y") double y);

    @Modifying
//    @Query("update OrderLocationEntity set finishLocation=:point" +
//            " where OrderLocationEntity.id=OrdersEntity.orderLocationId and OrdersEntity.id=:orderId")
//    @Query("update OrdersEntity o set o.orderLocationId.finishLocation = :point where o.id = :orderId")
    @Query("update OrderLocationEntity ol " +
            "set ol.finishLocation = :point " +
            "where ol = (select o.orderLocationId from OrdersEntity o where o.id = :orderId)")
    void updateOrderDestination(@Param(value = "orderId") Integer orderId, @Param(value = "point") PGpoint point);


    @Modifying
    @Query("update OrderLocationEntity ol " +
            "set ol.currentLocation = :point " +
            "where ol = (select o.orderLocationId from OrdersEntity o where o.id = :orderId)")
    void updateOrderLocation(@Param(value = "orderId") Integer orderId, @Param(value = "point") PGpoint point);

}
