package com.opensw.safeguard.controller.login;


import com.opensw.safeguard.domain.dto.MemberLoginDTO;

import com.opensw.safeguard.security.token.TokenInfo;
import com.opensw.safeguard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/safe")
public class LoginController {
    private final MemberService memberService;


    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginDTO memberLoginDTO){

        TokenInfo tokenInfo = memberService.login(memberLoginDTO.getUsername(),memberLoginDTO.getPassword());

        return tokenInfo;
    }




}
