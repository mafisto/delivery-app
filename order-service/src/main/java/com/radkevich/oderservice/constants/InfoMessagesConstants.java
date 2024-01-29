package com.radkevich.oderservice.constants;

public class InfoMessagesConstants {


    public static final String COMMON_ERROR_LOG_MESSAGE = "[ORDER-SERVICE-I001] Произошла ошибка при выполнении запроса: [{}] {}. Детали доступны при "
            + "уровне логгирования DEBUG";
    public static final String CREATE_ORDER_MESSAGE = "[ORDER-SERVICE-I002] Входящий запрос на создание нового заказа: {} ";
    public static final String CHANGE_DESTINATION_ORDER_MESSAGE = "[ORDER-SERVICE-I003] Входящий запрос на смену пункта назначения: {} ";
    public static final String CANCEL_ORDER_MESSAGE = "[ORDER-SERVICE-I004] Входящий запрос на отмену заказа: {} ";
    public static final String CHANGE_STATUS_ORDER_MESSAGE = "[ORDER-SERVICE-I005] Входящий запрос на смену статуса: {} ";
    public static final String ASSIGN_COURIER_ORDER_MESSAGE = "[ORDER-SERVICE-I006] Входящий запрос на назначение курьера заказу: {} ";
    public static final String CHANGE_ORDER_LOCATION_MESSAGE = "[ORDER-SERVICE-I007] Входящий запрос на изменение локации заказа: {} ";
    public static final String ORDER_CREATED_MESSAGE = "[ORDER-SERVICE-I008] Создан новый заказ: {} ";
    public static final String CREATE_COURIER_MESSAGE = "[ORDER-SERVICE-I009] Создан новый курьер: {} ";

}
