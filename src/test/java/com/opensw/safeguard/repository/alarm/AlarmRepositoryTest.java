package com.opensw.safeguard.repository.alarm;

import com.opensw.safeguard.domain.Alarm;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.repository.member.MemberRepository;
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
    void saveTest() {
        // given
        ArrayList<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        Member member = Member.builder()
                .username("id")
                .password("password")
                .roles(roles)
                .email("asc@naver.com")
                .build();
        memberRepository.save(member);

        Alarm alarm = Alarm.builder()
                .message("테스트 알림입니다.")
                .alarmType(AlarmType.WARN)
                .member(member)
                .build();

        // when
        Alarm savedAlarm = alarmRepository.save(alarm);

        // then
        assertThat(savedAlarm.getIsRead()).isFalse();
    }

}