package com.radkevich.usersservice.services.impl;

import com.radkevich.usersservice.constants.InfoMessagesConstants;
import com.radkevich.usersservice.dto.SignUpRequestDto;
import com.radkevich.usersservice.entity.UsersEntity;
import com.radkevich.usersservice.exception.UserAlreadyExistsException;
import com.radkevich.usersservice.repository.RolesRepository;
import com.radkevich.usersservice.repository.UsersRepository;
import com.radkevich.usersservice.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @Override
    public Integer createUserFromRequest(SignUpRequestDto request) {

        Optional<UsersEntity> userCheck = usersRepository.findByUsername(request.getUsername());
        if (userCheck.isEmpty()) {
            UsersEntity entity = new UsersEntity();
            entity.setVip(false);
            entity.setActive(true);
            entity.setPassword(request.getPassword());
            entity.setEmail(request.getEmail());
            entity.setMobileNumber(request.getMobilenumber());
            entity.setUsername(request.getUsername());
            entity.setRoleId(rolesRepository.findByRole("user").orElse(null));
            UsersEntity result = usersRepository.save(entity);
            log.debug(InfoMessagesConstants.USER_CREATED, result.getUsername());
            return result.getId();
        } else {
            throw new UserAlreadyExistsException(request.getUsername());
        }
    }
}
