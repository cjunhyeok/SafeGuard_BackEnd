package com.opensw.safeguard.service.devicetoken;

import com.opensw.safeguard.domain.DeviceToken;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.devicetoken.DeviceTokenRepository;
import com.opensw.safeguard.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceTokenServiceImpl implements DeviceTokenService{

    private final DeviceTokenRepository deviceTokenRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long save(String deviceToken, Long memberId) {

        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not exist")
        );

        DeviceToken token = DeviceToken.builder()
                .token(deviceToken)
                .member(findMember)
                .build();

        DeviceToken savedDeviceToken = deviceTokenRepository.save(token);

        return savedDeviceToken.getId();
    }

    @Override
    public List<DeviceToken> findAll() {
        return deviceTokenRepository.findAll();
    }
}
