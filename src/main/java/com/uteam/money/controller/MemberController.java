package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.converter.MemberConverter;
import com.uteam.money.domain.Member;
import com.uteam.money.dto.Member.MemberRequestDTO;
import com.uteam.money.dto.Member.MemberResponseDTO;
import com.uteam.money.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;


    @PostMapping("/signUp")
    public ApiResponse<MemberResponseDTO.signUpResultDTO> signUp(@RequestBody MemberRequestDTO.signUp request) {
        Member member = memberService.signUp(request);
        return ApiResponse.onSuccess(MemberConverter.signUpResultDTO(member));
    }

    // 중복확인
    @PostMapping("/checkId")
    public boolean checkId(@RequestBody MemberRequestDTO.checkId request) {
        boolean isUnique = memberService.checkId(request.getMemberId());

        if (isUnique) {
            return true;
        }
        return false;
    }
}
