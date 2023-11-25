package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.converter.AppMemberConverter;
import com.uteam.money.domain.AppMember;
import com.uteam.money.dto.AppMember.appMemberRequestDTO;
import com.uteam.money.dto.AppMember.appMemberResponseDTO.appMemberInviteResultDTO;
import com.uteam.money.service.AppMember.AppMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appmembers")
public class AppMemberController {
    private final AppMemberService appMemberService;

    @PostMapping("/createAppmember/{memberIdx}")
    public ApiResponse<appMemberInviteResultDTO> createAppMember(@PathVariable Long memberIdx, @RequestBody appMemberRequestDTO.createAppMemberDto request) {
        AppMember appMember = appMemberService.createAppMember(memberIdx, request);
        return ApiResponse.onSuccess(AppMemberConverter.toAddResultDTO(appMember));
    }
}
