package com.radkevich.adminservice.services;

import com.radkevich.adminservice.config.DockerContainers;
import com.radkevich.adminservice.dto.CourierDto;
import com.radkevich.adminservice.dto.CourierStatus;
import com.radkevich.adminservice.dto.CreateCourierDto;
import com.radkevich.adminservice.exceptions.CourierNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
public class CourierServiceTest extends DockerContainers {

    @Autowired
    private CouriersService couriersService;


    @Test
    public void getAllCouriers_SUCCESS_test() {
        CourierDto dto1 = new CourierDto();
        dto1.setUserName("courier1");
        dto1.setEmail("courier1@example.com");
        dto1.setMobileNumber("5555555555");
        dto1.setActive(true);
        dto1.setStatus(CourierStatus.IN_TRANSIT);

        CourierDto dto2 = new CourierDto();
        dto2.setUserName("courier2");
        dto2.setEmail("courier2@example.com");
        dto2.setMobileNumber("52352");
        dto2.setActive(true);
        dto2.setStatus(CourierStatus.IN_TRANSIT);
        List<CourierDto> couriersOrig = List.of(dto1, dto2);

        List<CourierDto> couriersDb = couriersService.getAllCouriers();

        Assertions.assertEquals(couriersOrig, couriersDb);
    }

    @Test
    public void getCourier_SUCCESS_test() {
        Integer courierId = 4;

        CourierDto couriersOrig = new CourierDto();
        couriersOrig.setUserName("courier2");
        couriersOrig.setEmail("courier2@example.com");
        couriersOrig.setMobileNumber("52352");
        couriersOrig.setActive(true);
        couriersOrig.setStatus(CourierStatus.IN_TRANSIT);

        CourierDto courierDb = couriersService.getCourier(courierId);
        Assertions.assertEquals(couriersOrig, courierDb);
    }

    @Test
    public void getCourier_FAIL_test() {
        Integer courierId = 2;
        Assertions.assertThrows(CourierNotFoundException.class, () -> couriersService.getCourier(courierId));
    }

    @Test
    public void createCourier_SUCCESS_test() {
        CreateCourierDto dto = CreateCourierDto.builder()
                .active(true)
                .email("courier-test@example.com")
                .mobilenumber("12345641")
                .password("password")
                .username("test-courier")
                .build();
        Assertions.assertDoesNotThrow(() -> couriersService.createCourier(dto));
    }


}
