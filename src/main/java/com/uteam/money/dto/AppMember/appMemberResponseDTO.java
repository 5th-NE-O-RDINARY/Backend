package com.uteam.money.dto.AppMember;

import com.uteam.money.domain.AppMember;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class appMemberResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class appMemberInviteResultDTO {
        Long appMemberIdx;
        LocalDateTime createdAt;
    }

}
