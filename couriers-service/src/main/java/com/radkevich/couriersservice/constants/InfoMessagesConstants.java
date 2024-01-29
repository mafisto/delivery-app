package com.radkevich.couriersservice.constants;

public class InfoMessagesConstants {


    public static final String COMMON_ERROR_LOG_MESSAGE = "[COURIER-SERVICE-I001] Произошла ошибка при выполнении запроса: [{}] {}. Детали доступны при "
            + "уровне логгирования DEBUG";

    public static final String COMMON_ERROR_LOG_DETAIL_MESSAGE = "[COURIER-SERVICE-I002] Детали ошибки: ";

    public final static String LOGIN_USER = "[COURIER-SERVICE-I003] Авторизация: {}";
    public static final String TOKEN_RECEIVED = "[COURIER-SERVICE-I004] Получен токен для пользователя: {}";

    public static final String MESSAGE_SENT = "[COURIER-SERVICE-I005] Сообщение было отправлено в брокер: {} с оффсетом: {}";
    public static final String ORDERS_RECEIVED = "[COURIER-SERVICE-I006] Получен список заказов размером: {} ";
    public static final String UPDATE_ORDER_LOCATION = "[COURIER-SERVICE-I007] Изменена локация заказа: {} ";
    public static final String CHANGE_STATUS_ORDER = "[COURIER-SERVICE-I009] Статус аказа id={} был изменен на {}";
    public static final String ORDER_RECEIVED = "[COURIER-SERVICE-I010] Получен заказ: {} ";

}
