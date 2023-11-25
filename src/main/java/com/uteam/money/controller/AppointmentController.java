package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.service.AppointmentService;
import com.uteam.money.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final LocationService locationService;

    @PostMapping("/create")
    public ApiResponse<?> createAppointment(@RequestBody AppointmentRequestDTO.createDTO request){
        Long memberIdx = 1L;
        Location location = locationService.createLocation(request);
        Appointment appointment = appointmentService.createAppointment(memberIdx, request, location);


        return ApiResponse.onSuccess(AppointmentConverter.toCreateResultDTO(appointment));
    }
}
