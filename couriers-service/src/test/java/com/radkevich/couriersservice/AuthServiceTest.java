package com.radkevich.couriersservice;


import com.radkevich.couriersservice.config.DockerContainers;
import com.radkevich.couriersservice.dto.SignInRequestDto;
import com.radkevich.couriersservice.exceptions.BadCredentialsException;
import com.radkevich.couriersservice.services.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest extends DockerContainers {
    @Autowired
    private AuthService authService;

    @Test
    public void signin_USER_FOUND_test() {
        SignInRequestDto request = SignInRequestDto.builder()
                .username("courier1")
                .build();
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.getToken(request));
    }

    @Test
    public void signin_USER_NOT_FOUND_test() {
        SignInRequestDto request = SignInRequestDto.builder()
                .username("courier-wrong")
                .build();
        Assertions.assertThrows(UsernameNotFoundException.class, () -> authService.getToken(request));
    }

}
