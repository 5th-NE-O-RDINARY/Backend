package com.uteam.money.dto.AppMember;

import com.uteam.money.domain.AppMember;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import lombok.Data;

@Data
public class appMemberRequestDTO {
    private String inviteCode;

    public AppMember toEntity(Appointment appointment, Member member) {
        AppMember appMember = AppMember.builder()
                .appointment(appointment)
                .member(member)
                .arrivalButton(arrivalButtonStatus.INACTIVE)
                .build();
        return appMember;
    }
}
