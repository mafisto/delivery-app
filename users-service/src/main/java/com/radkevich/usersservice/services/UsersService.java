package com.radkevich.usersservice.services;

import com.radkevich.usersservice.dto.SignUpRequestDto;

public interface UsersService {


    /**
     * Создаёт нового пользователя
     *
     * @param request данные для создания пользователя
     * @return идентификатор созданного пользователя
     */
    public Integer createUserFromRequest(SignUpRequestDto request);

}
