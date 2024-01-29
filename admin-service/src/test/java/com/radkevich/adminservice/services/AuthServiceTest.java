package com.radkevich.adminservice.services;


import com.radkevich.adminservice.config.DockerContainers;
import com.radkevich.adminservice.dto.SignInRequestDto;
import com.radkevich.adminservice.exceptions.BadCredentialsException;
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
                .username("admin1")
                .build();
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.getToken(request));
    }

    @Test
    public void signin_USER_NOT_FOUND_test() {
        SignInRequestDto request = SignInRequestDto.builder()
                .username("admin-wrong")
                .build();
        Assertions.assertThrows(UsernameNotFoundException.class, () -> authService.getToken(request));
    }

}
