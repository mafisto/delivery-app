package com.radkevich.couriersservice.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос токена доступа")
public class JwtRequestDto {

    @Schema(description = "Имя пользователя", example = "John")
    private String client_id;
    @Schema(description = "Пароль", example = "password")
    private String client_secret;
    @Schema(description = "grant_type", example = "client_credentials")
    private String grant_type;
}
