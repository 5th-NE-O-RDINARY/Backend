package com.uteam.money.service.Member;

import com.uteam.money.domain.Member;
import com.uteam.money.dto.Member.MemberRequestDTO;

public interface MemberService {
    Member signUp(MemberRequestDTO.signUp request);
    boolean checkId(String memberId);

    Member chargePoint(Long memberIdx, MemberRequestDTO.chargePoint request);

    Member showMemberInfo(Long memberIdx);
}
