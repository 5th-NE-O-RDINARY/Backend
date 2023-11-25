package com.uteam.money.service.test;

import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;

public interface testService {
    AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTO(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request);
    AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTOButton(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request);
}
