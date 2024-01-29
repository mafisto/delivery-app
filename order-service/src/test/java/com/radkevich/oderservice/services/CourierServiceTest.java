package com.radkevich.oderservice.services;


import com.radkevich.oderservice.config.DockerContainers;
import com.radkevich.oderservice.dto.CreateCourierDto;
import com.radkevich.oderservice.dto.broker.CreateCourierMessage;
import com.radkevich.oderservice.entity.CouriersEntity;
import com.radkevich.oderservice.repository.CouriersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class CourierServiceTest extends DockerContainers {


    @Autowired
    private CouriersRepository couriersRepository;
    @Autowired
    private CourierService courierService;


    @Test
    public void createCourier_SUCCESS_test() {
        CreateCourierMessage msg = CreateCourierMessage.builder().data(CreateCourierDto.builder()
                        .active(false)
                        .email("email@dot.com")
                        .password("password")
                        .mobilenumber("123465")
                        .username("test-courier")
                        .build())
                .build();

        courierService.createCourier(msg);

        Optional<CouriersEntity> entity = couriersRepository.findByUsername(msg.getData().getUsername());

        Assertions.assertTrue(entity.isPresent());
        Assertions.assertEquals(msg.getData().getActive(), entity.get().getActive());
        Assertions.assertEquals(msg.getData().getEmail(), entity.get().getEmail());
        Assertions.assertEquals(msg.getData().getPassword(), entity.get().getPassword());
        Assertions.assertEquals(msg.getData().getMobilenumber(), entity.get().getMobileNumber());
        Assertions.assertEquals(msg.getData().getUsername(), entity.get().getUsername());
    }

}
