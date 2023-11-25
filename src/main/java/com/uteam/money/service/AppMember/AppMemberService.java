package com.uteam.money.service.AppMember;

import com.uteam.money.domain.AppMember;
import com.uteam.money.dto.AppMember.appMemberRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;

public interface AppMemberService {
    AppMember createAppMember(Long userIdx, appMemberRequestDTO.createAppMemberDto request);
}
