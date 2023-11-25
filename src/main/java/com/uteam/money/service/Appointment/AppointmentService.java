package com.uteam.money.service.Appointment;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;

public interface AppointmentService {

    public Appointment createAppointment(Long memberIdx, AppointmentRequestDTO.createDTO request, Location location);
}
