package com.uteam.money.dto.Member;

import lombok.Getter;

public class MemberRequestDTO {
    @Getter
    public static class signUp {
        String name;
        String memberId;
        String password;
    }

    @Getter
    public static class checkId {
        String memberId;
    }
}
