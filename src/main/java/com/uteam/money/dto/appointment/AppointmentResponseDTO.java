package com.uteam.money.dto.appointment;

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
    public static class RefundResultDTO{
        Long memberIdx;
        Long appointmentIdx;
        Integer refundFee;
    }

}
