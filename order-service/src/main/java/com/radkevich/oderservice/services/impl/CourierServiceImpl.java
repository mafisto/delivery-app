package com.radkevich.oderservice.services.impl;

import com.radkevich.oderservice.dto.CourierStatus;
import com.radkevich.oderservice.dto.CreateCourierDto;
import com.radkevich.oderservice.dto.broker.CreateCourierMessage;
import com.radkevich.oderservice.entity.CourierStatusesEntity;
import com.radkevich.oderservice.entity.CouriersEntity;
import com.radkevich.oderservice.exceptions.CreateCourierException;
import com.radkevich.oderservice.repository.CourierStatusRepository;
import com.radkevich.oderservice.repository.CouriersRepository;
import com.radkevich.oderservice.services.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CouriersRepository couriersRepository;
    private final CourierStatusRepository courierStatusRepository;

    @Override
    @Transactional
    public void createCourier(CreateCourierMessage msg) {
        try {
            CreateCourierDto data = msg.getData();
            CourierStatusesEntity courierStatus = courierStatusRepository.findByStatus(CourierStatus.AVAILABLE.getValue());
            CouriersEntity entity = new CouriersEntity();
            entity.setActive(data.getActive());
            entity.setEmail(data.getEmail());
            entity.setMobileNumber(data.getMobilenumber());
            entity.setPassword(data.getPassword());
            entity.setUsername(data.getUsername());

            entity.setCourierStatusId(courierStatus);
            couriersRepository.save(entity);
        } catch (Exception ex) {
            throw new CreateCourierException(ex, msg);
        }
    }
}
