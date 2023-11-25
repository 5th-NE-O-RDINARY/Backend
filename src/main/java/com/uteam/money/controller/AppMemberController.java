package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.dto.AppMember.appMemberResponseDTO;
import com.uteam.money.dto.AppMember.appMemberResponseDTO.appMemberInviteResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appmembers")
public class AppMemberController {

//    @PostMapping("/createAppmember/{userIdx}")
//    public ApiResponse<appMemberInviteResultDTO> createAppMember(@PathVariable Long userIdx) {
//
//    }
}
