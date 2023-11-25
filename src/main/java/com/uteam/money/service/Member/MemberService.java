package com.uteam.money.service.Member;

import com.uteam.money.domain.Member;
import com.uteam.money.dto.Member.MemberRequestDTO;

public interface MemberService {
    Member signUp(MemberRequestDTO.signUp request);
    boolean checkId(String memberId);
}
