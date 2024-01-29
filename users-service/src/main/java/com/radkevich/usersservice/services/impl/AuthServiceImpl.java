package com.radkevich.usersservice.services.impl;

import com.radkevich.usersservice.constants.InfoMessagesConstants;
import com.radkevich.usersservice.dto.JwtRequestDto;
import com.radkevich.usersservice.dto.JwtResponseDto;
import com.radkevich.usersservice.dto.SignInRequestDto;
import com.radkevich.usersservice.entity.UsersEntity;
import com.radkevich.usersservice.exception.BadCredentialsException;
import com.radkevich.usersservice.exception.UserNotFoundException;
import com.radkevich.usersservice.feign.AuthClient;
import com.radkevich.usersservice.repository.UsersRepository;
import com.radkevich.usersservice.services.AuthService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthClient authClient;
    private final UsersRepository usersRepository;

    @Override
    public JwtResponseDto getToken(SignInRequestDto request) {
        Optional<UsersEntity> person = usersRepository.findByUsername(request.getUsername());
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
