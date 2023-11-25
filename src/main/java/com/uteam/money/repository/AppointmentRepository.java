package com.uteam.money.repository;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByInviteCodeContaining(String inviteCode);

    List<Appointment> findAllByMember(Member member);
}
