package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.DiffAppointment;
import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.service.AppMember.AppMemberService;
import com.uteam.money.service.Appointment.AppointmentService;
import com.uteam.money.service.DiffAppointment.DiffAppointmentService;
import com.uteam.money.service.Location.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final LocationService locationService;
    private final DiffAppointmentService diffAppointmentService;

    @PostMapping("/create/{memberIdx}")
    public ApiResponse<?> createAppointment(@PathVariable("memberIdx") Long memberIdx, @RequestBody AppointmentRequestDTO.createDTO request){

        Location location = locationService.createLocation(request);
        Appointment appointment = appointmentService.createAppointment(memberIdx, request, location);

        // 차등 방식
        if(request.getPayMethod().equals(2)) {
            DiffAppointment diffApp = diffAppointmentService.createDiffApp(appointment, request.getInterval());
        }

        return ApiResponse.onSuccess(AppointmentConverter.toCreateResultDTO(appointment));
    }
}
