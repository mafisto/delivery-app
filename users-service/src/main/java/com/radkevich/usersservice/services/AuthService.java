package com.radkevich.usersservice.services;

import com.radkevich.usersservice.dto.JwtResponseDto;
import com.radkevich.usersservice.dto.SignInRequestDto;

public interface AuthService {

    /**
     * Получение токена авторизации для пользователя
     * @param request
     * @return
     */
    public JwtResponseDto getToken(SignInRequestDto request);









}
