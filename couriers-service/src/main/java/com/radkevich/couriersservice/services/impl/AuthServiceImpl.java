package com.radkevich.couriersservice.services.impl;

import com.radkevich.couriersservice.constants.InfoMessagesConstants;
import com.radkevich.couriersservice.dto.JwtRequestDto;
import com.radkevich.couriersservice.dto.JwtResponseDto;
import com.radkevich.couriersservice.dto.SignInRequestDto;
import com.radkevich.couriersservice.entity.CouriersEntity;
import com.radkevich.couriersservice.exceptions.BadCredentialsException;
import com.radkevich.couriersservice.exceptions.UserNotFoundException;
import com.radkevich.couriersservice.feign.AuthClient;
import com.radkevich.couriersservice.repository.CouriersRepository;
import com.radkevich.couriersservice.services.AuthService;
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
    private final CouriersRepository couriersRepository;

    @Override
    public JwtResponseDto getToken(SignInRequestDto request) {
        Optional<CouriersEntity> person = couriersRepository.findByUsername(request.getUsername());
        if (person.isEmpty()) {
            throw new UserNotFoundException(request.getUsername());
        }
        try {
            JwtRequestDto requestDto = JwtRequestDto.builder()
                    .client_id(request.getUsername())
                    .client_secret(request.getPassword())
                    .grant_type(CLIENT_CREDENTIALS.getValue())
                    .build();
            JwtResponseDto token = authClient.getToken(requestDto);
            log.debug(InfoMessagesConstants.TOKEN_RECEIVED, request.getUsername());
            return token;
        } catch (FeignException ex){
            throw new BadCredentialsException(ex);
        }
    }
}
