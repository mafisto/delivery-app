package com.radkevich.usersservice.controllers;

import com.radkevich.usersservice.constants.InfoMessagesConstants;
import com.radkevich.usersservice.dto.JwtResponseDto;
import com.radkevich.usersservice.dto.SignInRequestDto;
import com.radkevich.usersservice.dto.SignUpRequestDto;
import com.radkevich.usersservice.services.AuthService;
import com.radkevich.usersservice.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@Slf4j
@RestController
@RequestMapping(value = AuthController.ROOT_WEB_CONTEXT)
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
@SecurityRequirement(name = "SwaggerAuthorization")
public class AuthController {
    public static final String ROOT_WEB_CONTEXT = "/api/v1/auth";
    private final AuthService authService;
    private final UsersService usersService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping(value = "/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid SignUpRequestDto request) {
        usersService.createUserFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping(value = "/sign-in")
    public JwtResponseDto signIn(@RequestBody @Valid SignInRequestDto request) {
        log.debug(InfoMessagesConstants.LOGIN_USER, request.getUsername());
        JwtResponseDto token = authService.getToken(request);
        return token;
    }


}