package com.radkevich.adminservice.services.impl;


import com.radkevich.adminservice.constants.InfoMessagesConstants;
import com.radkevich.adminservice.dto.JwtRequestDto;
import com.radkevich.adminservice.dto.JwtResponseDto;
import com.radkevich.adminservice.dto.SignInRequestDto;
import com.radkevich.adminservice.entity.AdminsEntity;
import com.radkevich.adminservice.exceptions.BadCredentialsException;
import com.radkevich.adminservice.exceptions.UserNotFoundException;
import com.radkevich.adminservice.feign.AuthClient;
import com.radkevich.adminservice.repository.AdminsRepository;
import com.radkevich.adminservice.services.AuthService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthClient authClient;
    private final AdminsRepository adminsRepository;

    @Override
    public JwtResponseDto getToken(SignInRequestDto request) {
        Optional<AdminsEntity> person = adminsRepository.findByUsername(request.getUsername());
        if (person.isEmpty()) {
            throw new UserNotFoundException(request.getUsername());
        }
        try {
            JwtRequestDto requestDto = JwtRequestDto.builder().client_id(request.getUsername())
                    .client_secret(request.getPassword())
                    .grant_type(CLIENT_CREDENTIALS.getValue())
                    .build();
            JwtResponseDto token = authClient.getToken(requestDto);
            log.debug(InfoMessagesConstants.TOKEN_RECEIVED, request.getUsername());
            return token;
        } catch (FeignException ex) {
            throw new BadCredentialsException(ex);
        }
    }
}
