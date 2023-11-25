package com.uteam.money.service.Member;

import com.uteam.money.apiPayload.GeneralException;
import com.uteam.money.apiPayload.code.status.ErrorStatus;
import com.uteam.money.converter.MemberConverter;
import com.uteam.money.domain.Member;
import com.uteam.money.domain.MemberImg;
import com.uteam.money.dto.Member.MemberRequestDTO;
import com.uteam.money.repository.MemberImgRepository;
import com.uteam.money.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberImgRepository memberImgRepository;
    @Override
    @Transactional
    public Member signUp(MemberRequestDTO.signUp request) {
        MemberImg randomProfileImg = findRandomImg();
        return memberRepository.save(MemberConverter.toMember(randomProfileImg, request));
    }

    public MemberImg findRandomImg() {
        MemberImg randomImg = memberImgRepository.findRandomEntity(1);
        if (randomImg != null) {
            return randomImg;
        } else {
            throw new GeneralException(ErrorStatus.PROFILEIMG_NOT_FOUND);
        }
    }
}
