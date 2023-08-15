package com.opensw.safeguard.service.devicetoken;

import com.opensw.safeguard.domain.DeviceToken;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.devicetoken.DeviceTokenRepository;
import com.opensw.safeguard.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceTokenServiceTest {

    @InjectMocks
    private DeviceTokenServiceImpl deviceTokenService;
    @Mock
    private DeviceTokenRepository deviceTokenRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void saveTest() {
        // given
        String deviceToken = "token";
        Long memberId = 1L;

        // stub
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(Member.builder().build()));
        when(deviceTokenRepository.save(any(DeviceToken.class))).thenReturn(DeviceToken.builder().build());

        // when
        Long savedDeviceTokenId = deviceTokenService.save(deviceToken, memberId);

        // then
        verify(memberRepository).findById(memberId);
        verify(deviceTokenRepository).save(any(DeviceToken.class));
    }
}
