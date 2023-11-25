package com.uteam.money.service.AppMember;

import com.uteam.money.domain.Appointment;
import com.uteam.money.dto.appointment.AppointmentResponseDTO;

public interface CalLateFeeService {

    public Integer calSettleUpLateFee(Long appointmentIdx);

    public Integer calCommonLateFee(Appointment appointment);

    public Integer calDiffLateFee(Appointment appointment);

    public Integer notLateMemberCountByCommon(Integer totalLateFee, Appointment appointment);

    public Integer notLateMemberCountByDiff(Integer totalLateFee, Appointment appointment);

    public AppointmentResponseDTO.RefundResultDTO calRefundFee(Long memberIdx, Long appointmentIdx, Integer settleUpLateFee);
}
