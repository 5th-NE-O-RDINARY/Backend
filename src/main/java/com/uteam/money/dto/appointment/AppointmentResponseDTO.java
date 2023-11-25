package com.uteam.money.dto.appointment;

import com.uteam.money.domain.Location;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO{
        Long appIdx;
        LocalDateTime createdAt;
        String inviteCode;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor

    public static class AppointmentPreviewListDTO {
        Long appIdx;
        String title;
        LocalDateTime date;
        Location location;
        String category; // 인증 방법
        String payMethod; // 납부 방법
        List<AppointMentMemberPreviewDTO> memberList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppointMentMemberPreviewDTO {
        Long appMemberIdx;
        Long memberIdx;
        String name;
        String profileImg;
        Integer lateTime;
        arrivalButtonStatus status;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundResultDTO{
        Long memberIdx;
        Long appointmentIdx;
        Integer refundFee;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IsOneHourLeftDTO{
        Boolean isLessThanOneHourLeft;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class expectedAppListDTO {
        Long appIdx;
        String latitude;
        String longitude;
        String date; // 약속 날짜와 시간
        List<AppMembersDTO> memberList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppMembersDTO {
        Long memberIdx;
        String profileImg;
        String name;
    }
}
