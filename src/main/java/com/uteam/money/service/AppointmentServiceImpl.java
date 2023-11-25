package com.uteam.money.service;

import com.uteam.money.apiPayload.GeneralException;
import com.uteam.money.apiPayload.code.status.ErrorStatus;
import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.domain.Member;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.repository.AppointmentRepository;
import com.uteam.money.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public Appointment createAppointment(Long memberIdx ,AppointmentRequestDTO.createDTO request, Location location){

        // 초대 코드 생성
        String inviteCode = generateInviteCode(7);

        // Member 찾기
        Optional<Member> member = memberRepository.findById(memberIdx);

        // Appointment 생성
        if(member.isPresent()){
            Appointment newAppointment = AppointmentConverter.toAppointment(member.get(), request, location, inviteCode);
            return appointmentRepository.save(newAppointment);
        }
        else{
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
    }

    public String generateInviteCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random rnd = new Random();
        while (length-- > 0) {
            result.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return result.toString();
    }

}
