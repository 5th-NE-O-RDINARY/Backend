package com.uteam.money.dto.AppMember;


import lombok.Getter;

public class appMemberRequestDTO {
    @Getter
    public static class createAppMemberDto {
        String inviteCode;
    }
}
