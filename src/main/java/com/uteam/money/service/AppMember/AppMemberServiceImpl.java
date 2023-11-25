package com.uteam.money.service.AppMember;

import static com.uteam.money.apiPayload.code.status.ErrorStatus.APP_MEMBER_NOT_FOUND;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.apiPayload.GeneralException;
import com.uteam.money.apiPayload.code.status.ErrorStatus;
import com.uteam.money.converter.AppMemberConverter;
import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
import com.uteam.money.dto.AppMember.appMemberRequestDTO;
import com.uteam.money.repository.AppMemberRepository;
import com.uteam.money.repository.AppointmentRepository;
import com.uteam.money.repository.MemberRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppMemberServiceImpl implements AppMemberService{
    private final AppMemberRepository appMemberRepository;
    private final AppointmentRepository appointmentRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public AppMember createAppMember(Long memberIdx, appMemberRequestDTO.createAppMemberDto request) {
        Appointment appointment = findAppointment(request.getInviteCode());
        
        if (!Objects.equals(appointment.getInviteCode(), request.getInviteCode())) {
            throw new GeneralException(APP_MEMBER_NOT_FOUND);
        }
        Member member = memberRepository.getReferenceById(memberIdx);
        return appMemberRepository.save(AppMemberConverter.toAppMember(appointment, member));
    }

    public Appointment findAppointment(String inviteCode) {
        Appointment appointment = appointmentRepository.findByInviteCodeContaining(inviteCode);
        if (appointment != null) {
            return appointment;
        } else {
            throw new GeneralException(ErrorStatus.APPOINTMENT_NOT_FOUND);
        }
    }
}
