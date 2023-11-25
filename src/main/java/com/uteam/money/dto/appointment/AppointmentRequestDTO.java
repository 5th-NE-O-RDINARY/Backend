package com.uteam.money.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uteam.money.domain.enums.Category;
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

        String latitude;

        String longitude;

        Integer category;

        Integer lateFee;
    }
}
