package com.radkevich.oderservice.exceptions.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(description = "Ошибка")
public class ErrorResponse {

    @Schema(description = "Код ошибки", example = "ERROR-E001")
    @Size(max = 255)
    @NotEmpty
    String code;

    @Schema(description = "Заголовок", example = "Непредвиденная ошибка")
    @Size(max = 255)
    @NotEmpty
    String title;

    @Schema(description = "Сообщение", example = "Попробуйте повторить запрос позже")
    @Size(max = 255)
    String message;


}
