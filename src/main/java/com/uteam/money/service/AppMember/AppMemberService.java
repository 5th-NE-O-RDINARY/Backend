package com.uteam.money.service.AppMember;

import com.uteam.money.domain.AppMember;
import com.uteam.money.dto.AppMember.appMemberRequestDTO;

public interface AppMemberService {
    AppMember createAppMember(Long userIdx, appMemberRequestDTO.createAppMemberDto request);
}
