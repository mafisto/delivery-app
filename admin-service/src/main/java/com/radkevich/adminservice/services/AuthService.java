package com.radkevich.adminservice.services;

import com.radkevich.adminservice.dto.JwtResponseDto;
import com.radkevich.adminservice.dto.SignInRequestDto;

public interface AuthService {
    /**
     * Получение токена авторизации для пользователя
     *
     * @param request
     * @return
     */
    public JwtResponseDto getToken(SignInRequestDto request);
}
