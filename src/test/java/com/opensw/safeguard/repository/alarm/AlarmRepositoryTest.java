package com.opensw.safeguard.repository.alarm;

import com.opensw.safeguard.domain.Alarm;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class AlarmRepositoryTest {

    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("알림 리포지토리 저장 테스트")
    void saveTest() {
        // given
        ArrayList<String> roles = new ArrayList<>();
        Member member = Member.builder()
                .username("id")
                .password("password")
                .roles(roles)
                .email("adf@naver.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Alarm alarm = Alarm.builder()
                .message("테스트 알림입니다.")
                .alarmType(AlarmType.WARN)
                .member(savedMember)
                .build();

        // when
        Alarm savedAlarm = alarmRepository.save(alarm);

        // then
        assertThat(savedAlarm.getIsRead()).isFalse();
        assertThat(savedAlarm.getMessage()).isEqualTo("테스트 알림입니다.");
        assertThat(savedAlarm.getAlarmType()).isEqualTo(AlarmType.WARN);
    }

    @Test
    @DisplayName("알림 리포지토리 PK 조회 테스트")
    void findByIdTest() {
        // given
        ArrayList<String> roles = new ArrayList<>();
        Member member = Member.builder()
                .username("id")
                .password("password")
                .roles(roles)
                .email("adf@naver.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Alarm alarm = Alarm.builder()
                .message("테스트 알림입니다.")
                .alarmType(AlarmType.WARN)
                .member(savedMember)
                .build();
        Alarm savedAlarm = alarmRepository.save(alarm);

        // when
        Alarm findAlarm = alarmRepository.findById(savedAlarm.getId()).orElseThrow(
                () -> new IllegalArgumentException()
        );

        // then
        assertThat(findAlarm).isEqualTo(savedAlarm);
    }

    @Test
    @DisplayName("알림 리포지토리 PK 조회 실패 테스트")
    void findByIdFailTest() {
        // given
        ArrayList<String> roles = new ArrayList<>();
        Member member = Member.builder()
                .username("id")
                .password("password")
                .roles(roles)
                .email("adf@naver.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Alarm alarm = Alarm.builder()
                .message("테스트 실패 알림입니다.")
                .alarmType(AlarmType.WARN)
                .member(savedMember)
                .build();
        Alarm savedAlarm = alarmRepository.save(alarm);

        // when // then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> alarmRepository.findById(savedAlarm.getId() + 1).orElseThrow(
                        () -> new IllegalArgumentException()
                ));
    }

}