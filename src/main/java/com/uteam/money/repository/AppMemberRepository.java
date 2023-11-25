package com.uteam.money.repository;

import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 3eb048850fcd89a75138e0575f8f1e7e0b2eb7e9
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppMemberRepository extends JpaRepository<AppMember, Long> {

    List<AppMember> findByAppointment(Appointment appointment);

    List<AppMember> findAllByAppointment(Appointment appointment);


    AppMember findByMember(Member member);
}
