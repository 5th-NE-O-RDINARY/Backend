package com.uteam.money.repository;

import com.uteam.money.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByInviteCodeContaining(String inviteCode);
}
