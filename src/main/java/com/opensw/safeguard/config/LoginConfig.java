package com.opensw.safeguard.config;

import com.opensw.safeguard.repository.member.MemberRepository;
import com.opensw.safeguard.security.token.JwtTokenProvider;
import com.opensw.safeguard.service.login.MemberLoginService;
import com.opensw.safeguard.service.login.MemberLoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@RequiredArgsConstructor
public class LoginConfig {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public MemberLoginService memberLoginService(){
        return new MemberLoginServiceImpl(memberRepository,authenticationManagerBuilder,jwtTokenProvider);
    }
}
