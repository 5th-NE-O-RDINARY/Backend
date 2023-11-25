package com.uteam.money.repository;

import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppMemberRepository extends JpaRepository<AppMember, Long> {
    List<AppMember> findAllByAppointment(Appointment appointment);

    AppMember findByMember(Member member);
}
