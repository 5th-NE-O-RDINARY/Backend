package com.uteam.money.service.DiffAppointment;

import com.uteam.money.domain.Appointment;
import com.uteam.money.domain.DiffAppointment;
import com.uteam.money.repository.DiffAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiffAppointmentServiceImpl implements DiffAppointmentService{

    private final DiffAppointmentRepository diffAppointmentRepository;

    @Override
    @Transactional
    public DiffAppointment createDiffApp(Appointment appointment, Integer interval) {
        DiffAppointment diffAppointment = DiffAppointment.builder()
                .appointment(appointment)
                .interval(interval).build();
        return diffAppointmentRepository.save(diffAppointment);
    }
}
