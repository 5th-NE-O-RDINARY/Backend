package com.uteam.money.converter;

import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import com.uteam.money.dto.AppMember.appMemberResponseDTO;
import java.time.LocalDateTime;

public class AppMemberConverter {
    public static appMemberResponseDTO.appMemberInviteResultDTO toAddResultDTO(AppMember appMember) {
        return appMemberResponseDTO.appMemberInviteResultDTO.builder()
                .appMemberIdx(appMember.getAppMemberIdx())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static AppMember toAppMember(Appointment appointment, Member member) {
        return AppMember.builder()
                .member(member)
                .appointment(appointment)
                .arrivalButton(arrivalButtonStatus.INACTIVE)
                .build();
    }
}
