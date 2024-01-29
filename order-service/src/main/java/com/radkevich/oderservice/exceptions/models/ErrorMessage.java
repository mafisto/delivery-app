package com.radkevich.oderservice.exceptions.models;

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

    GENERAL_ERROR("ORDER-SERVICE-E001", "Непредвиденная ошибка", "Попробуйте повторить запрос позднее"),
    ASSIGN_COURIER_ERROR("ORDER-SERVICE-E002", "Ошибка с назначением курьра заказу", "Такого заказа или курьера не существует: {}"),
    CHANGE_COORDINATES_ERROR("ORDER-SERVICE-E003", "Ошибка со сменой локации ордера", "Такого заказа не существует или проблема с координатми: {}"),
    CHANGE_ORDER_STATUS_ERROR("ORDER-SERVICE-E004", "Ошибка со сменой статуса ордера", "Такого заказа или статуса не существует или нет прав: {}"),
    CREATE_ORDER_ERROR("ORDER-SERVICE-E005", "Ошибка с созданием заказа", "Возникла ошибка во время создания заказа: {}"),
    INSUFFICIENT_RIGHTS_ERROR("ORDER-SERVICE-E006", "Недостаточно прав", "Отсутствуют права для выполнения данного запроса. Необходимы права: {}"),
    ORDER_NOT_FOUND_ERROR("ORDER-SERVICE-E007", "Заказ не найден", "Такого заказа не существует"),
    CREATE_COURIER_ERROR("ORDER-SERVICE-E008", "Ошибка с созданием курьера", "Не получилось добавить нового курьера: {}");


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
