package com.uteam.money.service;

import com.uteam.money.converter.LocationConverter;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Location createLocation(AppointmentRequestDTO.createDTO request){

        // Location 생성
        Location newLocation = LocationConverter.toLocation(request);
        return locationRepository.save(newLocation);
    }

}
