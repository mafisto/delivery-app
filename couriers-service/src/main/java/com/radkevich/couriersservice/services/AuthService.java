package com.radkevich.couriersservice.services;

import com.radkevich.couriersservice.dto.JwtResponseDto;
import com.radkevich.couriersservice.dto.SignInRequestDto;

public interface AuthService {

    /**
     * Получение токена авторизации для пользователя
     * @param request
     * @return
     */
   JwtResponseDto getToken(SignInRequestDto request);

}
