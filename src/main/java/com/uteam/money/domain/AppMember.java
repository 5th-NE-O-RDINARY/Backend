package com.uteam.money.domain;

import com.uteam.money.domain.common.BaseEntity;
import com.uteam.money.domain.enums.arrivalButtonStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appMemberIdx;
    @Enumerated(EnumType.STRING)
    private arrivalButtonStatus arrivalButton;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_idx")
    private Appointment appointment;
    private Integer lateTime;

    public void setStatus(arrivalButtonStatus arrivalButton) {
        this.arrivalButton = arrivalButton;
    }

    public Integer setLateTime(int lateTime) {
        this.lateTime = lateTime;
        return lateTime;
    }
}
