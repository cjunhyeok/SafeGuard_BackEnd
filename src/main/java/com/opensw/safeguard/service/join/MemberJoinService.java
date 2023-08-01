package com.opensw.safeguard.service.join;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberJoinService {
    private final MemberRepository memberRepository;
    @Transactional
    public Member join(String memberId, String password , String email){
        Member member = Member.builder()
                .username(memberId)
                .password(password)
                .email(email)
                .roles(List.of("USER"))
                .build();
        return memberRepository.save(member);

    }

    @Transactional
    public boolean duplicateCheckUsername(String username){
        return memberRepository.existsByUsername(username);
    }
}
