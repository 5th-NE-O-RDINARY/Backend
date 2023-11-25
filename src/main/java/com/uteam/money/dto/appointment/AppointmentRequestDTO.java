package com.uteam.money.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uteam.money.domain.enums.Category;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class AppointmentRequestDTO {

    @Getter
    public static class createDTO{

        String title;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime time;

        Integer payMethod;

        String latitude;

        String longitude;

        Integer category;

        Integer lateFee;

        Integer interval; // 차등 간격
    }

    @Getter
    public static class dateDTO {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime time;
    }
}
