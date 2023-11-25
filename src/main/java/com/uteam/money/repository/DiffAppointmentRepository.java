package com.uteam.money.repository;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.DiffAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiffAppointmentRepository extends JpaRepository<DiffAppointment, Long> {
    DiffAppointment findByAppointment(Appointment appointment);
}
