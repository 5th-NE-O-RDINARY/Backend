package com.uteam.money.domain;

import com.uteam.money.domain.common.BaseEntity;
import com.uteam.money.domain.enums.AppointmentStatus;
import com.uteam.money.domain.enums.Category;
import com.uteam.money.domain.enums.PayMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appIdx;

    private String title;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer lateFee;

    private String inviteCode;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @OneToOne
    @JoinColumn(name = "loc_idx")
    private Location location;
}
