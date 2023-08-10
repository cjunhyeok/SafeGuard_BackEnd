package com.opensw.safeguard.controller.member;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.domain.dto.EmailConfirmDTO;
import com.opensw.safeguard.domain.dto.MemberJoinDTO;
import com.opensw.safeguard.domain.dto.MemberLoginDTO;
import com.opensw.safeguard.email.AuthCode;
import com.opensw.safeguard.email.EmailService;
import com.opensw.safeguard.security.token.TokenInfo;
import com.opensw.safeguard.service.member.MemberService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/safe")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService mailService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginDTO memberLoginDTO){

        TokenInfo tokenInfo = memberService.login(memberLoginDTO.getUsername(),memberLoginDTO.getPassword());

        return tokenInfo;
    }
    @PostMapping("/join")
    public Member join(@RequestBody MemberJoinDTO memberJoinDTODTO){

        return memberService.join(memberJoinDTODTO.getUsername(), memberJoinDTODTO.getPassword(), memberJoinDTODTO.getEmail());


    }

    @PostMapping("/join/emailConfirm")
    public AuthCode emailConfirm(@RequestBody EmailConfirmDTO emailConfirmDTO) throws MessagingException, UnsupportedEncodingException {

        return mailService.sendEmail(emailConfirmDTO.getEmail());

    }

    @PostMapping("/join/duplicate")
    public DuplicateUsername duplicate(@RequestBody DuplicateUsername duplicateUsername){
        return memberService.duplicateCheckUsername(duplicateUsername.getUsername());

    }
    @PostMapping("/test")
    public void test(@AuthenticationPrincipal User memberContext){
        log.info(memberContext.toString());
    }
}
