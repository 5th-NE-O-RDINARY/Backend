package com.uteam.money.service.test;

import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;
import com.uteam.money.repository.AppMemberRepository;
import com.uteam.money.repository.AppointmentRepository;
import com.uteam.money.repository.MemberRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class testServiceImpl implements testService{
    private final AppointmentRepository appointmentRepository;
    private final AppMemberRepository appMemberRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTO(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request) {

        Member member = memberRepository.getReferenceById(memberIdx);
        Appointment appointment = appointmentRepository.getReferenceById(appIdx);
        List<AppMember> members = appMemberRepository.findByAppointment(appointment);

        AppMember appMember = appMemberRepository.findByMember(member);
        // 위치 기반
        if ("LOCATION".equals(appointment.getCategory().toString())) {
            int timeTotime = Math.toIntExact(appointmentCheck(appIdx, request.getTime()));

            // 도착을 하지 않았으면 자동으로 빨강 (지각)
            if (timeTotime < 0 ) {
                appMember.setStatus(arrivalButtonStatus.RED);
                int absValue = Math.abs(timeTotime);
                appMember.setLateTime(absValue);
            } else {
                // 도착을 했다는 뜻
                appMember.setStatus(arrivalButtonStatus.GRAY);
                appMember.setLateTime(0);
            }
            return AppointmentConverter.appPreviewListDTO(appointment, members);
        } else {
            getAppListButton(memberIdx, appIdx, request);
        }
        return AppointmentConverter.appPreviewListDTO(appointment, members);
    }

    public Long appointmentCheck(Long appIdx, LocalDateTime time) {
        Appointment appointment = appointmentRepository.getReferenceById(appIdx);

        Duration duration = Duration.between(time, appointment.getDate());
        long seconds = duration.toMinutes();

        return seconds;
    }

    // 버튼 기반일 때
    public AppointmentResponseDTO.AppointmentPreviewListDTO getAppListButton(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request) {
//            // 약속 시간 전 도착: 방장 수동
        return null;
    }
}
