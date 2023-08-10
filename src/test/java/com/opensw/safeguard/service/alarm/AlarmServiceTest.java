package com.opensw.safeguard.service.alarm;

import com.opensw.safeguard.domain.Alarm;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.alarm.AlarmRepository;
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
class AlarmServiceTest {

    @InjectMocks
    private AlarmServiceImpl alarmService;
    @Mock
    private AlarmRepository alarmRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void saveAlarmTest() {
        // given
        Long memberId = 1L;
        String message = "Message Test";
        AlarmType alarmType = AlarmType.WARN;

        // stub
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(Member.builder().build()));
        when(alarmRepository.save(any(Alarm.class))).thenReturn(Alarm.builder().build());

        // when
        Long savedAlarmId = alarmService.saveAlarm(message, alarmType, memberId);

        // then
        verify(memberRepository).findById(memberId);
        verify(alarmRepository).save(any(Alarm.class));
    }
}