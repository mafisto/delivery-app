package com.radkevich.adminservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "Запрос на создание нового курьера")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCourierDto {

    @Schema(description = "Имя курьера", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;

    @Schema(description = "Телефон", example = "88005553535")
    @Size(max = 15, message = "Длина телефона не может быть больше 15 цифр")
    @NotBlank(message = "Телефон не может быть пустыми")
    private String mobilenumber;

    @Schema(description = "Активный ли аккаунт", example = "true")
    @NotBlank(message = "Телефон не может быть пустыми")
    private Boolean active;
}