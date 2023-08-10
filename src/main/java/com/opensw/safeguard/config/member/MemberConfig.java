package com.opensw.safeguard.config.member;

import com.opensw.safeguard.repository.member.MemberRepository;
import com.opensw.safeguard.security.token.JwtTokenProvider;
import com.opensw.safeguard.service.member.MemberService;
import com.opensw.safeguard.service.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@RequiredArgsConstructor
public class MemberConfig {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository,authenticationManagerBuilder,jwtTokenProvider);
    }
}
