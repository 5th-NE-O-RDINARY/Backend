package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.DiffAppointment;
import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;
import com.uteam.money.service.AppMember.CalLateFeeService;
import com.uteam.money.service.Appointment.AppointmentService;
import com.uteam.money.service.DiffAppointment.DiffAppointmentService;
import com.uteam.money.service.Location.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final LocationService locationService;
    private final DiffAppointmentService diffAppointmentService;
    private final CalLateFeeService calLateFeeService;

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

    // 환급비 계산
    @GetMapping("/arrival/finish/{appointmentIdx}/{memberIdx}")
    public ApiResponse<?> calculateAppointmentLateFee(@PathVariable("memberIdx") Long memberIdx, @PathVariable("appointmentIdx") Long appointmentIdx){
        Integer settleUpLateFee = calLateFeeService.calSettleUpLateFee(appointmentIdx); // 지각 안 한 사람이 받을 추가 비용
        AppointmentResponseDTO.RefundResultDTO refundResultDTO = calLateFeeService.calRefundFee(memberIdx, appointmentIdx, settleUpLateFee); // 환급비 계산

        return ApiResponse.onSuccess(refundResultDTO);
    }


    // 한 시간 이하로 남았는지
    @PostMapping("/time/onehourleft/{memberIdx}/{appIdx}")
    public ApiResponse<AppointmentResponseDTO.AppointmentPreviewListDTO> isLessThanOneHourLeft(@PathVariable Long memberIdx ,@PathVariable("appIdx") Long appIdx, @RequestBody AppointmentRequestDTO.dateDTO request){
        AppointmentResponseDTO.AppointmentPreviewListDTO check = appointmentService.isMoreThanOneHourLeft(memberIdx, appIdx, request);
        return ApiResponse.onSuccess(check);
    }

    @GetMapping("/{memberIdx}/{appIdx}")
    public ApiResponse<AppointmentResponseDTO.AppointmentPreviewListDTO> getAppList(@PathVariable Long memberIdx , @PathVariable Long appIdx, @RequestBody
    AppointmentRequestDTO.dateDTO request) {
        AppointmentResponseDTO.AppointmentPreviewListDTO result = appointmentService.getAppPreviewListDTO(memberIdx, appIdx ,request);
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/button/{memberIdx}/{appIdx}")
    public ApiResponse<AppointmentResponseDTO.AppointmentPreviewListDTO> getAppListStatusButton(@PathVariable Long memberIdx , @PathVariable Long appIdx, @RequestBody
    AppointmentRequestDTO.dateDTO request) {
        AppointmentResponseDTO.AppointmentPreviewListDTO result = appointmentService.getAppPreviewListDTOButton(memberIdx, appIdx, request);
        return ApiResponse.onSuccess(result);
    }
}
