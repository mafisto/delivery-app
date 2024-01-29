package com.radkevich.adminservice.services.impl;

import com.radkevich.adminservice.constants.InfoMessagesConstants;
import com.radkevich.adminservice.converters.CourierConverter;
import com.radkevich.adminservice.dto.CourierDto;
import com.radkevich.adminservice.dto.CreateCourierDto;
import com.radkevich.adminservice.entity.CouriersEntity;
import com.radkevich.adminservice.exceptions.CourierNotFoundException;
import com.radkevich.adminservice.exceptions.UserAlreadyExistsException;
import com.radkevich.adminservice.repository.CouriersRepository;
import com.radkevich.adminservice.services.CouriersService;
import com.radkevich.adminservice.services.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouriersServiceImpl implements CouriersService {

    private final CouriersRepository couriersRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void createCourier(CreateCourierDto dto) {
        Optional<CouriersEntity> entity = couriersRepository.findByUsername(dto.getUsername());
        if (entity.isEmpty()) {
            kafkaProducerService.createCourier(dto);
            log.debug(InfoMessagesConstants.COURIER_CREATED, dto.getUsername());
        } else {
            throw new UserAlreadyExistsException(dto.getUsername());
        }
    }

    @Override
    public CourierDto getCourier(Integer id) {
        Optional<CouriersEntity> courier = couriersRepository.findById(id);
        if (courier.isPresent()) {
            CourierDto dto = CourierConverter.convertToDto(courier.get());
            return dto;
        } else {
            throw new CourierNotFoundException(id);
        }
    }

    @Override
    public List<CourierDto> getAllCouriers() {
        List<CouriersEntity> entities = couriersRepository.findAll();
        List<CourierDto> dtos = CourierConverter.convertToDtos(entities);
        return dtos;
    }
}
