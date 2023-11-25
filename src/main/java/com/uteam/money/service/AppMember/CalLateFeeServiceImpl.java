package com.uteam.money.service.AppMember;

import com.uteam.money.apiPayload.GeneralException;
import com.uteam.money.apiPayload.code.status.ErrorStatus;
import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.DiffAppointment;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.enums.AppointmentStatus;
import com.uteam.money.domain.enums.PayMethod;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;
import com.uteam.money.repository.AppMemberRepository;
import com.uteam.money.repository.AppointmentRepository;
import com.uteam.money.repository.DiffAppointmentRepository;
import com.uteam.money.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CalLateFeeServiceImpl implements CalLateFeeService{

    private final AppointmentRepository appointmentRepository;
    private final AppMemberRepository appMemberRepository;
    private final DiffAppointmentRepository diffAppointmentRepository;
    private final MemberRepository memberRepository;

    @Override
    public Integer calSettleUpLateFee(Long appointmentIdx) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentIdx);
        if(optionalAppointment.isPresent()){
            Appointment appointment = optionalAppointment.get();
            appointment.setStatus(AppointmentStatus.INACTIVE);
            Integer totalLateFee = 0;

            // 총 지각비 계산
            if(appointment.getPayMethod().equals(PayMethod.COMMON)){ // 공통 지각비
                totalLateFee = calCommonLateFee(appointment);
            }
            else if(appointment.getPayMethod().equals(PayMethod.DIFF)){ // 차등 지각비
                totalLateFee = calDiffLateFee(appointment);
            }

            // 지각 안 한 사람끼리 n빵 정산
            Integer notLateCount = 0;
            if(appointment.getPayMethod().equals(PayMethod.COMMON)){ // 공통 지각비
                notLateCount = notLateMemberCountByCommon(totalLateFee, appointment); // 나눠가질 사람 수
            }
            else if(appointment.getPayMethod().equals(PayMethod.DIFF)){ // 차등 지각비
                notLateCount = notLateMemberCountByDiff(totalLateFee, appointment); // 나눠가질 사람 수
            }

            Integer settleUpLateFee = 0; // n빵한 비용
            if(notLateCount!=0){
                settleUpLateFee = totalLateFee / notLateCount;
            }
            return settleUpLateFee;
        }
        else{
            throw new GeneralException(ErrorStatus.APPOINTMENT_NOT_FOUND);
        }
    }

    @Override
    public Integer calCommonLateFee(Appointment appointment) {
        Integer totalLateFee = 0;

        List<AppMember> allByAppointment = appMemberRepository.findAllByAppointment(appointment);
        for (AppMember appMember : allByAppointment) {
            Integer lateTime = appMember.getLateTime();
            if(lateTime != 0){ // 지각한 사람
                log.info("lateTIme = {}", lateTime);
                log.info("memberId = {}", appMember.getAppMemberIdx());
                totalLateFee += appointment.getLateFee();
            }
        }
        return totalLateFee;
    }

    @Override
    public Integer calDiffLateFee(Appointment appointment) {
        Integer totalLateFee = 0;

        List<AppMember> allByAppointment = appMemberRepository.findAllByAppointment(appointment);
        DiffAppointment diffAppointment = diffAppointmentRepository.findByAppointment(appointment);
        Integer interval = diffAppointment.getInterval(); // 차등 간격
        
        for (AppMember appMember : allByAppointment) {
            Integer lateTime = appMember.getLateTime();
            if(lateTime != 0){ // 지각한 사람
                // interval이 5일 때: lateTime이 5~9면 5분으로 간주, 10~14면 10분으로 간주
                // interval이 10일 때: lateTime이 10~19면 10분으로 간주, 20~29면 20분으로 간주
                Integer calculatedLateTime = (lateTime / interval) * interval;
                Integer maxLateFee = appointment.getLateFee();
                Integer tempLateFee = (maxLateFee / 10) * (calculatedLateTime / interval);
                if(tempLateFee > maxLateFee){ // 계산된 게 최대 지각비 보다 클 경우
                    tempLateFee = maxLateFee;
                }
                totalLateFee += tempLateFee; // 계산된 지각 요금을 총 요금에 더함
            }
        }

        return totalLateFee;
    }

    @Override
    public Integer notLateMemberCountByCommon(Integer totalLateFee, Appointment appointment) {
        Integer notLateCount = 0;

        List<AppMember> allByAppointment = appMemberRepository.findAllByAppointment(appointment);
        for (AppMember appMember : allByAppointment) {
            Integer lateTime = appMember.getLateTime();
            if(lateTime <= 0){ // 지각 안 한 사람
                notLateCount++;
            }
        }

        return notLateCount;
    }

    @Override
    public Integer notLateMemberCountByDiff(Integer totalLateFee, Appointment appointment) {
        Integer notLateCount = 0;

        List<AppMember> allByAppointment = appMemberRepository.findAllByAppointment(appointment);
        DiffAppointment diffAppointment = diffAppointmentRepository.findByAppointment(appointment);
        Integer interval = diffAppointment.getInterval(); // 차등 간격

        for (AppMember appMember : allByAppointment) {
            Integer lateTime = appMember.getLateTime();
            if(lateTime < interval){ // 지각 안 한 사람
                notLateCount++;
            }
        }

        return notLateCount;
    }

    @Override
    @Transactional
    public AppointmentResponseDTO.RefundResultDTO calRefundFee(Long memberIdx, Long appointmentIdx, Integer settleUpLateFee) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentIdx);
        Optional<Member> optionalMember = memberRepository.findById(memberIdx);

        if(optionalMember.isPresent() && optionalAppointment.isPresent()){
            Appointment appointment = optionalAppointment.get();
            
            AppMember appMember = appMemberRepository.findByMember(optionalMember.get());
            DiffAppointment diffAppointment = diffAppointmentRepository.findByAppointment(appointment);
            Integer lateTime = appMember.getLateTime();
            Integer lateFee = appointment.getLateFee();

            Integer refundFee = 0;
            if(diffAppointment == null){ // 공통
                if(lateTime <= 0){ // 지각 안 한 사람
                    refundFee += settleUpLateFee + lateFee;
                }
            }else{ // 차등
                Integer interval = diffAppointment.getInterval(); // 차등 간격
                if(lateTime < interval){ // 지각 안 한 사람
                    refundFee += settleUpLateFee + lateFee;
                }
            }

            Member member = optionalMember.get();
            member.setReward(refundFee);

            AppointmentResponseDTO.RefundResultDTO refundResultDTO
                    = AppointmentResponseDTO.RefundResultDTO.builder()
                    .refundFee(refundFee)
                    .appointmentIdx(appointmentIdx)
                    .memberIdx(memberIdx)
                    .build();
            return refundResultDTO;

        }
        else{
            throw new GeneralException(ErrorStatus.APPOINTMENT_NOT_FOUND);
        }
    }
}
