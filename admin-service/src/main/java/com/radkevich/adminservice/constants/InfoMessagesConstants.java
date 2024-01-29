package com.radkevich.adminservice.constants;

public class InfoMessagesConstants {


    public static final String COMMON_ERROR_LOG_MESSAGE = "[ADMIN-SERVICE-I001] Произошла ошибка при выполнении запроса: [{}] {}. Детали доступны при "
            + "уровне логгирования DEBUG";

    public static final String COMMON_ERROR_LOG_DETAIL_MESSAGE = "[ADMIN-SERVICE-I002] Детали ошибки: ";

    public final static String LOGIN_USER = "[ADMIN-SERVICE-I003] Авторизация: {}";
    public static final String TOKEN_RECEIVED = "[ADMIN-SERVICE-I004] Получен токен для пользователя: {}";

    public static final String MESSAGE_SENT = "[ADMIN-SERVICE-I005] Сообщение было отправлено в брокер: {} с оффсетом: {}";
    public static final String ORDERS_RECEIVED = "[ADMIN-SERVICE-I006] Получен список заказов размером: {} ";
    public static final String CREATE_COURIER = "[ADMIN-SERVICE-I007] Добавление новго курьера: {} ";
    public static final String CHANGE_STATUS_ORDER = "[ADMIN-SERVICE-I009] Статус аказа id={} был изменен на {}";
    public static final String ORDER_RECEIVED = "[ADMIN-SERVICE-I010] Получен заказ: {} ";
    public static final String GET_ALL_COURIERS = "[ADMIN-SERVICE-I010] Получен списка всех курьеров длиной: {} ";
    public static final String GET_COURIER = "[ADMIN-SERVICE-I011] Получены данные о курьере: {} ";
    public static final String COURIER_CREATED = "[ADMIN-SERVICE-I012] Добавлен новый курьер: {} ";

}
