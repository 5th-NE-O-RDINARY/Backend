package com.uteam.money.service.Location;

import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;

public interface LocationService {
    public Location createLocation(AppointmentRequestDTO.createDTO request);
}
