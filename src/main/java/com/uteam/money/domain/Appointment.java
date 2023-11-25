package com.uteam.money.domain;

import com.uteam.money.domain.enums.AppointmentStatus;
import com.uteam.money.domain.enums.Category;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appIdx;

    private Date date;

    private String location;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer lateFee;

    private String inviteCode;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private AppointmentStatus status;
}
