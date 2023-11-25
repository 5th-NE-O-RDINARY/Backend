package com.uteam.money.converter;

import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.MemberImg;
import com.uteam.money.domain.enums.AppointmentStatus;
import com.uteam.money.domain.enums.Category;
import com.uteam.money.domain.enums.PayMethod;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;

import com.uteam.money.dto.appointment.AppointmentResponseDTO.AppointmentPreviewListDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentConverter {

    public static AppointmentResponseDTO.CreateResultDTO toCreateResultDTO(Appointment appointment){
        return AppointmentResponseDTO.CreateResultDTO.builder()
                .appIdx(appointment.getAppIdx())
                .createdAt(LocalDateTime.now())
                .inviteCode(appointment.getInviteCode())
                .build();
    }

    public static Appointment toAppointment(Member member, AppointmentRequestDTO.createDTO request, Location location, String inviteCode){
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        Category category = null;
        switch(request.getCategory()){
            case 1:
                category = Category.BUTTON;
                break;
            case 2:
                category = Category.LOCATION;
                break;
        }

        PayMethod payMethod = null;
        switch(request.getPayMethod()){
            case 1:
                payMethod = PayMethod.COMMON;
                break;
            case 2:
                payMethod = PayMethod.DIFF;
                break;
        }

        return Appointment.builder()
                .title(request.getTitle())
                .lateFee(request.getLateFee())
                .date(localDateTime)
                .category(category)
                .status(AppointmentStatus.ACTIVE)
                .location(location)
                .member(member)
                .inviteCode(inviteCode)
                .payMethod(payMethod)
                .build();
    }

    public static AppointmentResponseDTO.AppointMentMemberPreviewDTO appPreviewMemberDTO(Appointment app, AppMember appMember) {
        return AppointmentResponseDTO.AppointMentMemberPreviewDTO.builder()
                .appMemberIdx(appMember.getAppMemberIdx())
                .name(appMember.getMember().getName())
                .profileImg(app.getMember().getMemberImg().getImgUrl())
                .lateTime(appMember.getLateTime())
                .status(appMember.getArrivalButton())
                .build();
    }

    public static AppointmentResponseDTO.AppointmentPreviewListDTO appPreviewListDTO(Appointment appointment, List<AppMember> members) {
        List<AppointmentResponseDTO.AppointMentMemberPreviewDTO> memberList = members.stream()
                .map(member -> appPreviewMemberDTO(appointment, member))
                .collect(Collectors.toList());
        return AppointmentResponseDTO.AppointmentPreviewListDTO.builder()
                .appIdx(appointment.getAppIdx())
                .title(appointment.getTitle())
                .payMethod(appointment.getPayMethod().toString())
                .location(appointment.getLocation())
                .category(appointment.getCategory().toString())
                .date(appointment.getDate())
                .memberList(memberList)
                .build();

    }

    public static AppointmentResponseDTO.pastAppDTO pastAppListDTO(Appointment appointment, List<AppMember> appMembers, AppMember appMember){
        List<String> appMemberProfileList = new ArrayList<>();
        for (AppMember member : appMembers) {
            System.out.println("member.getAppMemberIdx() = " + member.getAppMemberIdx());
            String imgUrl = member.getMember().getMemberImg().getImgUrl();
            appMemberProfileList.add(imgUrl);
        }
        
        return AppointmentResponseDTO.pastAppDTO.builder()
                .title(appointment.getTitle())
                .appIdx(appointment.getAppIdx())
                .date(appointment.getDate())
                .lateTime(appMember.getLateTime())
                .appMemberProfileList(appMemberProfileList)
                .build();
    }
}
