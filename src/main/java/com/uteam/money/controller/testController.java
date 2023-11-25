package com.uteam.money.controller;

import com.uteam.money.apiPayload.ApiResponse;
import com.uteam.money.converter.AppointmentConverter;
import com.uteam.money.domain.Appointment;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;
import com.uteam.money.service.test.testService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tests")
public class testController {
    private final testService testService;

    @GetMapping("/{memberIdx}/{appIdx}")
    public ApiResponse<AppointmentResponseDTO.AppointmentPreviewListDTO> getAppList(@PathVariable Long memberIdx , @PathVariable Long appIdx, @RequestBody
                                                                                    AppointmentRequestDTO.dateDTO request) {
        AppointmentResponseDTO.AppointmentPreviewListDTO result = testService.getAppPreviewListDTO(memberIdx, appIdx ,request);
        return ApiResponse.onSuccess(result);
    }

    @GetMapping("/button/{memberIdx}/{appIdx}")
    public ApiResponse<AppointmentResponseDTO.AppointmentPreviewListDTO> getAppListStatusButton(@PathVariable Long memberIdx , @PathVariable Long appIdx, @RequestBody
    AppointmentRequestDTO.dateDTO request) {
        AppointmentResponseDTO.AppointmentPreviewListDTO result = testService.getAppPreviewListDTOButton(memberIdx, appIdx, request);
        return ApiResponse.onSuccess(result);
    }
}
