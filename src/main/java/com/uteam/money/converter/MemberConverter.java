package com.uteam.money.converter;

import com.uteam.money.domain.Member;
import com.uteam.money.domain.MemberImg;
import com.uteam.money.domain.enums.MemberStatus;
import com.uteam.money.dto.Member.MemberRequestDTO;
import com.uteam.money.dto.Member.MemberResponseDTO;
import java.time.LocalDateTime;

public class MemberConverter {
    public static MemberResponseDTO.signUpResultDTO signUpResultDTO(Member member) {
        return MemberResponseDTO.signUpResultDTO.builder()
                .memberIdx(member.getMemberIdx())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberImg memberImg, MemberRequestDTO.signUp request) {
        return Member.builder()
                .memberImg(memberImg)
                .memberId(request.getMemberId())
                .name(request.getName())
                .password(request.getPassword())
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public static MemberResponseDTO.pointResultDTO pointResultDTO(Member member){
        return MemberResponseDTO.pointResultDTO.builder().point(member.getPoint()).memberIdx(member.getMemberIdx()).build();
    }

    public static MemberResponseDTO.myPageResultDTO myPageResultDTO(Member member){
        return MemberResponseDTO.myPageResultDTO.builder()
                .memberIdx(member.getMemberIdx())
                .reward(member.getReward())
                .point(member.getPoint())
                .name(member.getName())
                .memberImgUrl(member.getMemberImg().getImgUrl())
                .build();
    }
}
