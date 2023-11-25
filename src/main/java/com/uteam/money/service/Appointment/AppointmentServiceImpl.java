package com.uteam.money.service.Appointment;

import com.uteam.money.apiPayload.GeneralException;
import com.uteam.money.apiPayload.code.status.ErrorStatus;
import com.uteam.money.converter.AppMemberConverter;
import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.enums.PayMethod;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;
import com.uteam.money.repository.AppMemberRepository;
import com.uteam.money.repository.AppointmentRepository;
import com.uteam.money.repository.MemberRepository;
import com.uteam.money.service.Appointment.AppointmentService;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final MemberRepository memberRepository;

    private final AppMemberRepository appMemberRepository;


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
            AppMember appMember = AppMemberConverter.toAppMember(newAppointment, member.get());
            appMemberRepository.save(appMember);
            return appointmentRepository.save(newAppointment);
        }
        else{
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }
    }

    @Override
    public AppointmentResponseDTO.AppointmentPreviewListDTO isMoreThanOneHourLeft(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request) {
        Member member = memberRepository.getReferenceById(memberIdx);
        Appointment appointment = appointmentRepository.getReferenceById(appIdx);
        List<AppMember> members = appMemberRepository.findByAppointment(appointment);

        //List<AppMember> appMember = appMemberRepository.findAllByMember(member);

        LocalDateTime currentTime = request.getTime();
        log.info("currentTime = {}", currentTime);

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appIdx);

        Appointment appointmentDay = optionalAppointment.get();
        LocalDateTime appointmentDate = appointmentDay.getDate();

        Duration duration = Duration.between(currentTime, appointmentDate);
        if (duration.toMinutes() <= 60) {
            for (AppMember appMember : members) {
                appMember.setStatus(arrivalButtonStatus.INACTIVE);
                // Update the database to reflect the status change
                appMemberRepository.save(appMember);
            }

        }
        return AppointmentConverter.appPreviewListDTO(appointment, members);
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

    @Transactional
    public AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTO(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request) {

        Member member = memberRepository.getReferenceById(memberIdx);
        Appointment appointment = appointmentRepository.getReferenceById(appIdx);
        List<AppMember> members = appMemberRepository.findByAppointment(appointment);

        List<AppMember> appMembersForAppointmentAndMember = appMemberRepository.findAllByAppointmentAndMember(appointment, member);
        //AppMember appMember = appMemberRepository.findByMember(member);
        // 위치 기반
        if ("LOCATION".equals(appointment.getCategory().toString())) {
            int timeTotime = Math.toIntExact(appointmentCheck(appIdx, request.getTime()));

            for (AppMember appMember : appMembersForAppointmentAndMember) {
                // 도착을 하지 않았으면 자동으로 빨강 (지각)
                if (timeTotime < 0) {
                    appMember.setStatus(arrivalButtonStatus.RED);
                    int absValue = Math.abs(timeTotime);
                    appMember.setLateTime(absValue);
                } else {
                    // 도착을 했다는 뜻
                    appMember.setStatus(arrivalButtonStatus.GRAY);
                    appMember.setLateTime(0);
                }
                return AppointmentConverter.appPreviewListDTO(appointment, members);
            }
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
        Member member = memberRepository.getReferenceById(memberIdx);
        Appointment appointment = appointmentRepository.getReferenceById(appIdx);
        List<AppMember> members = appMemberRepository.findByAppointment(appointment);

        int timeTotime = Math.toIntExact(appointmentCheck(appIdx, request.getTime()));

        List<AppMember> appMembersForAppointmentAndMember = appMemberRepository.findAllByAppointmentAndMember(appointment, member);

        for (AppMember appMember : appMembersForAppointmentAndMember) {
            // 도착을 하지 않았으면 자동으로 빨강 (지각)
            if (timeTotime < 0) {
                appMember.setStatus(arrivalButtonStatus.RED);
                int absValue = Math.abs(timeTotime);
                appMember.setLateTime(absValue);
            }
        }
        return AppointmentConverter.appPreviewListDTO(appointment, members);
    }

    @Transactional
    public AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTOButton(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request) {
        Member member = memberRepository.getReferenceById(memberIdx);
        Appointment appointment = appointmentRepository.getReferenceById(appIdx);
        List<AppMember> members = appMemberRepository.findByAppointment(appointment);

        int timeTotime = Math.toIntExact(appointmentCheck(appIdx, request.getTime()));

        List<AppMember> appMembersForAppointmentAndMember = appMemberRepository.findAllByAppointmentAndMember(
                appointment, member);

        for (AppMember appMember : appMembersForAppointmentAndMember) {
            appMember.setStatus(arrivalButtonStatus.GRAY);
            int absValue = Math.abs(timeTotime);
            appMember.setLateTime(absValue);
        }
            return AppointmentConverter.appPreviewListDTO(appointment, members);
        }


    @Override
    public List<AppointmentResponseDTO.pastAppDTO> getPastAppList(Long memberIdx) {
        Optional<Member> optionalMember = memberRepository.findById(memberIdx);
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer appCount = 0;

        log.info("member = {}", optionalMember.get().toString());
        if(optionalMember.isPresent()){
            log.info("mmmm");
            Member member = optionalMember.get();

            List<Appointment> appointmentList = appointmentRepository.findAllByMember(member);
            List<AppointmentResponseDTO.pastAppDTO> pastAppDTOS = new ArrayList<>();
            log.info("appointmentList = {}", appointmentList.toString());


            for (Appointment appointment : appointmentList) {
                log.info("appointment = {}", appointment.getAppIdx());

                List<AppMember> appMemberList = appMemberRepository.findAllByAppointment(appointment);
                log.info("appMemberList = {}", appMemberList.stream().toList());

                AppMember appMember = appMemberRepository.findByMemberAndAppointment(member, appointment);
                appCount++;

                AppointmentResponseDTO.pastAppDTO pastAppDTO = AppointmentConverter.pastAppListDTO(appointment, appMemberList, appMember);
                pastAppDTOS.add(pastAppDTO);
            }
            return pastAppDTOS;
        }
        else{
            throw new GeneralException(ErrorStatus.MEMBER_NOT_FOUND);
        }

    }

}
