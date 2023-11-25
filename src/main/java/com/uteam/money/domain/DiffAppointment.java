package com.uteam.money.domain;

import com.uteam.money.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DiffAppointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diffAppIdx;

    private Integer interval;

    @OneToOne
    @JoinColumn(name = "app_idx")
    private Appointment appointment;
}
