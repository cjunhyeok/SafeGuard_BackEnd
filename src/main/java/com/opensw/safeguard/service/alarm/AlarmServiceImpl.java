package com.opensw.safeguard.service.alarm;

import com.opensw.safeguard.domain.Alarm;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.alarm.AlarmRepository;
import com.opensw.safeguard.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmServiceImpl implements AlarmService{

    private AlarmRepository alarmRepository;
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public Long saveAlarm(String message, AlarmType alarmType, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member Not exist")
        );

        Alarm alarm = Alarm.builder()
                .message(message)
                .alarmType(alarmType)
                .member(findMember)
                .build();

        Alarm savedAlarm = alarmRepository.save(alarm);

        return savedAlarm.getId();
    }
}
