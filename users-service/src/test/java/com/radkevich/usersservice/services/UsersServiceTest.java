package com.radkevich.usersservice.services;

import com.radkevich.usersservice.config.DockerContainers;
import com.radkevich.usersservice.dto.SignUpRequestDto;
import com.radkevich.usersservice.entity.UsersEntity;
import com.radkevich.usersservice.exception.UserAlreadyExistsException;
import com.radkevich.usersservice.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTest extends DockerContainers {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;


    @Test
    public void createUserFromRequest_SUCCESS_test() {
        SignUpRequestDto request = SignUpRequestDto.builder()
                .username("test-user").password("test-password")
                .email("test-email@example.com").mobilenumber("12321").build();

        Integer userId = usersService.createUserFromRequest(request);
        Optional<UsersEntity> createdUser = usersRepository.findById(userId);

        Assertions.assertTrue(createdUser.isPresent());
        Assertions.assertEquals(request.getEmail(), createdUser.get().getEmail());
        Assertions.assertEquals(request.getMobilenumber(), createdUser.get().getMobileNumber());
        Assertions.assertEquals(request.getPassword(), createdUser.get().getPassword());
        Assertions.assertEquals(request.getUsername(), createdUser.get().getUsername());

    }

    @Test
    public void createUserFromRequest_FAIL_test() {
        SignUpRequestDto request = SignUpRequestDto.builder()
                .username("user1").password("test-password")
                .email("test-email@example.com").mobilenumber("12321").build();


        Assertions.assertThrows(UserAlreadyExistsException.class, () -> usersService.createUserFromRequest(request));

    }

}
