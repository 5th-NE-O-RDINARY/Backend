package com.uteam.money.repository;

import com.uteam.money.domain.MemberImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberImgRepository extends JpaRepository<MemberImg, Long> {
    @Query(value = "SELECT * FROM member_img ORDER BY RAND() LIMIT 1", nativeQuery = true)
    MemberImg findRandomEntity(int count);
}
