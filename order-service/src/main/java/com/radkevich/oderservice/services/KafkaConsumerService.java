package com.radkevich.oderservice.services;

public interface KafkaConsumerService {

    /**
     * Читает из топика для создания заказа.
     *
     * @param msg
     */
    void createOrder(String msg);


    /**
     * Читает из топика для создания заказа.
     *
     * @param msg
     */
    void changeOrderDestination(String msg);

    /**
     * Читает из топика для отмены заказа.
     *
     * @param msg
     */
    void cancelOrder(String msg);


    /**
     * Читает из топика для смены сатуса заказа.
     *
     * @param msg
     */
    void changeOrderStatus(String msg);


    /**
     * Читает из топика для назначения заказу курьера.
     *
     * @param msg
     */
    void assignCourierToOrder(String msg);


    /**
     * Читает из топика для создания новго курьера.
     *
     * @param msg
     */
    void createCourier(String msg);


    /**
     * Читает из топика для изменение местоположения заказа.
     *
     * @param msg
     */
    void changeOrderCoordinates(String msg);


}
