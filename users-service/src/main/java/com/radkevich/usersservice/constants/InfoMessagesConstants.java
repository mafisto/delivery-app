package com.radkevich.usersservice.constants;

public class InfoMessagesConstants {


    public static final String COMMON_ERROR_LOG_MESSAGE = "[USER-SERVICE-I001] Произошла ошибка при выполнении запроса: [{}] {}. Детали доступны при "
            + "уровне логгирования DEBUG";
    public static final String COMMON_ERROR_LOG_DETAIL_MESSAGE = "[USER-SERVICE-I002] Детали ошибки: ";

    public final static String LOGIN_USER = "[USER-SERVICE-I003] Авторизация: {}";
    public static final String USER_CREATED = "[USER-SERVICE-I004] Пользователь создан: {}";
    public static final String MESSAGE_SENT = "[USER-SERVICE-I005] Сообщение было отправлено в брокер: {} с оффсетом: {}";
    public static final String ORDERS_RECEIVED = "[USER-SERVICE-I006] Получен список заказов размером: {} ";
    public static final String ORDER_RECEIVED = "[USER-SERVICE-I007] Получен заказ: {} ";
    public static final String UPDATE_ORDER_DEST = "[USER-SERVICE-I008] Изменены координаты заказа: {} ";
    public static final String CANCEL_ORDER = "[USER-SERVICE-I009] Заказа id={} был отменен";
    public static final String CREATE_ORDER = "[USER-SERVICE-I010] Создание нового заказа: {}";
    public static final String TOKEN_RECEIVED = "[USER-SERVICE-I011] Получен токен для пользователя: {}";
    public static final String ORDER_NOT_EXISTS = "[USER-SERVICE-I012] Такого заказа не существует: {}";
}
