package com.opensw.safeguard.controller.login;


import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.MemberJoinDTO;
import com.opensw.safeguard.domain.dto.MemberLoginDTO;
import com.opensw.safeguard.service.login.MemberService;
import com.opensw.safeguard.token.TokenInfo;
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

        TokenInfo tokenInfo = memberService.login(memberLoginDTO.getMemberId(),memberLoginDTO.getPassword());

        return tokenInfo;
    }

    @PostMapping("/join")
    public Member join(@RequestBody MemberJoinDTO memberJoinDTODTO){

        return memberService.join(memberJoinDTODTO.getMemberId(), memberJoinDTODTO.getPassword(), memberJoinDTODTO.getEmail());


    }
//    @PostMapping("/join/duplicate")
//    public boolean duplicate(@RequestBody String memberId){
//
//    }


}
