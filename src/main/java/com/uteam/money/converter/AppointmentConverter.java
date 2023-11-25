package com.uteam.money.converter;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.enums.AppointmentStatus;
import com.uteam.money.domain.enums.Category;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentConverter {

    public static AppointmentResponseDTO.CreateResultDTO toCreateResultDTO(Appointment appointment){
        return AppointmentResponseDTO.CreateResultDTO.builder()
                .appIdx(appointment.getAppIdx())
                .createdAt(LocalDateTime.now())
                .inviteCode(appointment.getInviteCode())
                .build();
    }

    public static Appointment toAppointment(Member member, AppointmentRequestDTO.createDTO request, Location location, String inviteCode){
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();
        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        Category category = null;
        switch(request.getCategory()){
            case 1:
                category = Category.BUTTON;
                break;
            case 2:
                category = Category.LOCATION;
                break;
        }

        return Appointment.builder()
                .title(request.getTitle())
                .lateFee(request.getLateFee())
                .date(localDateTime)
                .category(category)
                .status(AppointmentStatus.ACTIVE)
                .location(location)
                .member(member)
                .inviteCode(inviteCode)
                .build();
    }
}
