package com.uteam.money.service.DiffAppointment;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.DiffAppointment;

public interface DiffAppointmentService {
    DiffAppointment createDiffApp(Appointment appointment, Integer interval);
}
