package com.opensw.safeguard.controller.join;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.*;
import com.opensw.safeguard.email.AuthCode;
import com.opensw.safeguard.email.EmailService;


import com.opensw.safeguard.service.member.MemberService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/safe")
public class JoinController {

    private final MemberService memberService;
    private final EmailService mailService;
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
