package com.uteam.money.domain;

import com.uteam.money.domain.common.BaseEntity;
import com.uteam.money.domain.enums.AppointmentStatus;
import com.uteam.money.domain.enums.Category;
import jakarta.persistence.*;
import lombok.*;

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

    private Date date;

    private String location;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer lateFee;

    private String inviteCode;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;
}
