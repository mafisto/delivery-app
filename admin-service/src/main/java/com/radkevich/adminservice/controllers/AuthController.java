package com.radkevich.adminservice.controllers;


import com.radkevich.adminservice.constants.InfoMessagesConstants;
import com.radkevich.adminservice.dto.JwtResponseDto;
import com.radkevich.adminservice.dto.SignInRequestDto;
import com.radkevich.adminservice.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = AuthController.ROOT_WEB_CONTEXT)
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
@SecurityRequirement(name = "SwaggerAuthorization")
public class AuthController {
    public static final String ROOT_WEB_CONTEXT = "/api/v1/auth";
    private final AuthService authService;

    @Operation(summary = "Авторизация ")
    @PostMapping(value = "/sign-in")
    public JwtResponseDto signIn(@RequestBody @Valid SignInRequestDto request) {
        log.debug(InfoMessagesConstants.LOGIN_USER, request.getUsername());
        JwtResponseDto token = authService.getToken(request);
        return token;
    }


}