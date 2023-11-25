package com.uteam.money.dto.Member;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class signUpResultDTO {
        Long memberIdx;
        LocalDateTime createdAt;
    }
}
