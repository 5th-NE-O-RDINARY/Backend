package com.uteam.money.repository;

import com.uteam.money.domain.AppMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppMemberRepository extends JpaRepository<AppMember, Long> {
}
