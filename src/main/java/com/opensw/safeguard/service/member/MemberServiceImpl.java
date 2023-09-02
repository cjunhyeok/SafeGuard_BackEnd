package com.opensw.safeguard.service.member;

import com.opensw.safeguard.domain.Member;

import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.email.EmailService;
import com.opensw.safeguard.repository.member.MemberRepository;
import com.opensw.safeguard.security.token.JwtTokenProvider;
import com.opensw.safeguard.security.token.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    @Transactional
    public Member join(String memberId, String password, String email,
                       String realName, String phoneNumber, List<String> associatedPhoneNumber)
    {


        Member member = Member.builder()
                .username(memberId)
                .password(password)
                .email(email)
                .roles(List.of("USER"))
                .realName(realName)
                .phoneNumber(phoneNumber)
                .associatedPhoneNumber(associatedPhoneNumber)
                .build();


        return memberRepository.save(member);
    }

    @Override
    public DuplicateUsername duplicateCheckUsername(String username) {
        DuplicateUsername duplicateUsername = DuplicateUsername.
                builder()
                .username(username)
                .duplicate(memberRepository.existsByUsername(username))
                .build();
        return duplicateUsername;
    }

    @Override
    public TokenInfo login(String memberId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
   @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findByUsername(String username) {
        Member findMember = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Member not Exist")
        );
        return findMember;
    }

    @Override
    public boolean existByRealNameAndEmail(String realName,String email){
        return memberRepository.existsByRealNameAndEmail(realName,email);
    }
    @Override
    public Member findByRealName(String realName){
        Member findMember = memberRepository.findByRealName(realName).orElseThrow(
                () -> new IllegalArgumentException("Member not Exist")
        );
        return findMember;
    }
}
