package com.opensw.safeguard.config;

import com.opensw.safeguard.repository.member.MemberRepository;
import com.opensw.safeguard.service.join.MemberJoinService;
import com.opensw.safeguard.service.join.MemberJoinServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JoinConfig {
    private final MemberRepository memberRepository;
    @Bean
    public MemberJoinService memberJoinService(){
        return new MemberJoinServiceImpl(memberRepository);
    }

}
