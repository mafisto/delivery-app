package com.radkevich.couriersservice.exceptions.models;

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

    GENERAL_ERROR("COURIER-SERVICE-E001", "Непредвиденная ошибка", "Попробуйте повторить запрос позднее"),
    USER_AUTH_ERROR("COURIER-SERVICE-E002", "Проблема с получением токена", "Проблема с соединением или неверные данные"),
    SEND_MESSAGE_ERROR("COURIER-SERVICE-E003", "Проблема с отправкой сообщения", "Возникла проблема с отправкой сообщения в топик"),
    CANCEL_ORDER_ERROR("COURIER-SERVICE-E004", "Нельзя изменить статус заказа ", "Невозможно изменить статус заказа на: {}"),
    ORDER_NOT_FOUND_ERROR("COURIER-SERVICE-E005", "Заказ не найден", "Такого заказа не существует"),
    CHANGE_ORDER_STATUS_ERROR("COURIER-SERVICE-E006", "Ошибка со смнеой статуса заказа", "Нельзя сменить статус заказа"),
    USER_NOT_FOUND("COURIER-SERVICE-E007", "Невозможно найти пользователя", "Такого пользователя не существует: {}");


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
        return new ErrorResponse(errorMessage.getCode(), errorMessage.getTitle(), errorMessage.getMessage());
    }
    public ErrorResponse getErrorResponse(Object[] args) {
        return new ErrorResponse(getCode(), getTitle(), renderMessage(args));
    }
}
