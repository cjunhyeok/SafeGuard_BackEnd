package com.opensw.safeguard.controller.alarm;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.service.alarm.EmitterService;
import com.opensw.safeguard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmitterApiController {

    private final EmitterService emitterService;
    private final MemberService memberService;

    @GetMapping(value = "/api/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestHeader(value = "Last-Event-Id", required = false, defaultValue = " ") String lastEventId,
                                @AuthenticationPrincipal UserDetails userDetails) {

        String loginUsername = userDetails.getUsername();
        Member loginMember = memberService.findByUsername(loginUsername);

        return emitterService.subscribe(loginMember.getId());
    }
}
