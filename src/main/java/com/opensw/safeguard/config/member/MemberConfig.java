package com.opensw.safeguard.config.member;

import com.opensw.safeguard.email.EmailService;
import com.opensw.safeguard.repository.image.ImageRepository;
import com.opensw.safeguard.repository.member.MemberRepository;
import com.opensw.safeguard.security.token.JwtTokenProvider;
import com.opensw.safeguard.service.image.ImageHandler;
import com.opensw.safeguard.service.image.ImageService;
import com.opensw.safeguard.service.image.ImageServiceImpl;
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

    private final ImageHandler imageHandler;

    private final ImageRepository imageRepository;

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository,authenticationManagerBuilder,jwtTokenProvider);
    }

    @Bean
    public ImageService imageService(){
        return new ImageServiceImpl(imageHandler,imageRepository);
    }



}
