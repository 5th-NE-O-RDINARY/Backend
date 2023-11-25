package com.uteam.money.service.Appointment;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.Location;
import com.uteam.money.dto.appointment.AppointmentRequestDTO;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {

    public Appointment createAppointment(Long memberIdx, AppointmentRequestDTO.createDTO request, Location location);

    AppointmentResponseDTO.AppointmentPreviewListDTO isMoreThanOneHourLeft(Long memberIdx, Long appointmentIdx, AppointmentRequestDTO.dateDTO request);
    AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTO(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request);
    AppointmentResponseDTO.AppointmentPreviewListDTO getAppPreviewListDTOButton(Long memberIdx, Long appIdx, AppointmentRequestDTO.dateDTO request);

    public List<AppointmentResponseDTO.pastAppDTO> getPastAppList(Long memberIdx);
}
