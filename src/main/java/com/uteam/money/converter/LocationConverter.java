package com.uteam.money.converter;

import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;

public class LocationConverter {

    public static Location toLocation(AppointmentRequestDTO.createDTO request){

        return Location.builder()
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .build();
    }
}
