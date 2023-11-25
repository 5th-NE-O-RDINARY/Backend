package com.uteam.money.dto.Member;

import java.time.LocalDateTime;

import com.uteam.money.domain.MemberImg;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class pointResultDTO{
        Long memberIdx;
        Integer point;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class myPageResultDTO{
        Long memberIdx;
        String name;
        Integer point;
        Integer reward;
        String  memberImgUrl;
    }
}
