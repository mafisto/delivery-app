package com.radkevich.usersservice.services;


import com.radkevich.usersservice.config.DockerContainers;
import com.radkevich.usersservice.dto.JwtRequestDto;
import com.radkevich.usersservice.dto.JwtResponseDto;
import com.radkevich.usersservice.dto.SignInRequestDto;
import com.radkevich.usersservice.feign.AuthClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest extends DockerContainers {

    @MockBean
    private AuthClient authClient;

    @Autowired
    private AuthService authService;

    @Test
    public void getToken_valid_test() {
        JwtResponseDto validResponse = JwtResponseDto.builder().access_token("token").token_type("bearer").expires_in(300).build();
        JwtRequestDto request = JwtRequestDto.builder().client_id("user1").client_secret("password").grant_type("client_credentials").build();
        doReturn(validResponse).when(authClient).getToken(request);
        SignInRequestDto reqDTO = SignInRequestDto.builder().username("user1").password("password").build();
        JwtResponseDto serviceResponse = authService.getToken(reqDTO);
        Assertions.assertEquals(validResponse, serviceResponse);
    }

    @Test
    public void signin_USER_FOUND_test() {
        SignInRequestDto request = SignInRequestDto.builder()
                .username("user1")
                .build();
        Assertions.assertDoesNotThrow(() -> authService.getToken(request));
    }

    @Test
    public void signin_USER_NOT_FOUND_test() {
        SignInRequestDto request = SignInRequestDto.builder()
                .username("courier-wrong")
                .build();
        Assertions.assertThrows(UsernameNotFoundException.class, () -> authService.getToken(request));
    }

}
