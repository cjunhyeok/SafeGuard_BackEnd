package com.opensw.safeguard.controller.devicetoken;

import com.opensw.safeguard.controller.devicetoken.dto.SaveDeviceTokenRequest;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.service.devicetoken.DeviceTokenService;
import com.opensw.safeguard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;
    private final MemberService memberService;

    @PostMapping("/api/deviceToken/new")
    public ResponseEntity saveDeviceToken(@RequestBody SaveDeviceTokenRequest request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        String loginUsername = userDetails.getUsername();
        Member loginMember = memberService.findByUsername(loginUsername);

        Long savedDeviceTokenId = deviceTokenService.save(request.getDeviceToken(), loginMember.getId());
        return ResponseEntity.ok(savedDeviceTokenId);
    }
}