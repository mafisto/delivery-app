package com.radkevich.adminservice.exceptions.models;

import lombok.Getter;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Сообщения об ошибках.
 */
@Getter
public enum ErrorMessage {

    GENERAL_ERROR("ADMIN-SERVICE-E001", "Непредвиденная ошибка", "Попробуйте повторить запрос позднее"),
    USER_AUTH_ERROR("ADMIN-SERVICE-E002", "Проблема с получением токена", "Проблема с соединением или неверные данные"),
    SEND_MESSAGE_ERROR("ADMIN-SERVICE-E003", "Проблема с отправкой сообщения", "Возникла проблема с отправкой сообщения в топик"),
    ORDER_NOT_FOUND_ERROR("ADMIN-SERVICE-E004", "Заказ не найден", "Такого заказа не существует"),
    CHANGE_ORDER_STATUS_ERROR("ADMIN-SERVICE-E005", "Ошибка со смнеой статуса заказа", "Нельзя сменить статус заказа"),
    COURIER_NOT_FOUND("ADMIN-SERVICE-E006", "Курьер не найден", "Такого курьера не существует: {}"),
    USER_ALREADY_EXISTS("ADMIN-SERVICE-E007", "Невозможно создать нового пользователя", " Такой пользователя уже существует: {}"),
    ASSIGN_COURIER_TO_ORDER_ERROR("ADMIN-SERVICE-E008", "Невозможно назначить курьеру заказ", "Неверные параметры или такого пользователя или заказа не существует: courierId={}, orderId={}"),
    USER_NOT_FOUND("ADMIN-SERVICE-E009", "Невозможно найти пользователя", "Такого пользователя не существует: {}");


    private final String code;
    private final String title;
    private final String message;

    ErrorMessage(final String code, final String title, final String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }

    /**
     * Сводит текст заголовка сообщения и его параметры в единую строку.
     *
     * @param args аргументы лог-сообщения
     * @return готовый заголовок
     */
    public String renderTitle(Object... args) {
        Object[] strArgs = Arrays.stream(args)
                .map((Object t) -> Arrays.toString(args))
                .toArray();
        return MessageFormat.format(this.getTitle(), strArgs);
    }

    /**
     * Сводит текст сообщения и его параметры в единую строку.
     *
     * @param args аргументы лог-сообщения
     * @return готовый заголовок
     */
    public String renderMessage(Object... args) {
        Object[] strArgs = Arrays.stream(args).map(String::valueOf).toArray();
        FormattingTuple format = MessageFormatter.format(this.getMessage(), strArgs);
        return format.getMessage();
    }

    /**
     * Сводит код сообщения и заголовок в форматную строку для логов.
     *
     * @param args аргументы лог-сообщения
     * @return готовая строка
     */
    public String toLogMessage(Object... args) {
        return "[" + this.getCode() + "] " + this.renderTitle(args);
    }

    /**
     * Преобразует сообщение об ошибке в ответ
     *
     * @param errorMessage ошибка
     * @return готовый ответ
     */
    public static ErrorResponse toErrorResponse(ErrorMessage errorMessage) {
        return new ErrorResponse(errorMessage.getCode(), errorMessage.getTitle(), errorMessage.renderMessage());
    }

    public ErrorResponse getErrorResponse(Object[] args) {
        return new ErrorResponse(getCode(), getTitle(), renderMessage(args));
    }
}
