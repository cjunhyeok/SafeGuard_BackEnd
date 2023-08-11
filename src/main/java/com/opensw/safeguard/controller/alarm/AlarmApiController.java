package com.opensw.safeguard.controller.alarm;

import com.opensw.safeguard.controller.alarm.dto.AlarmDto;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.service.alarm.AlarmService;
import com.opensw.safeguard.service.alarm.EmitterService;
import com.opensw.safeguard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AlarmApiController {

    private final AlarmService alarmService;
    private final EmitterService emitterService;
    private final MemberService memberService;

    @PostMapping(value = "/api/alarm/{alarmType}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void alarm(@PathVariable(value = "alarmType")AlarmType alarmType) {

        List<Member> findAllMembers = memberService.findAll();

        if (findAllMembers.isEmpty()) {
            return ;
        }

        makeAndSendAlarm(alarmType, findAllMembers);
    }

    private void makeAndSendAlarm(AlarmType alarmType, List<Member> findAllMembers) {

        String message;

        if (alarmType.equals(AlarmType.WARN)) {
            message = "경고 알림입니다.";
            for (Member findAllMember : findAllMembers) {
                alarmService.saveAlarm(message, alarmType, findAllMember.getId());
                sendAlarm(alarmType, findAllMember, message);
            }
        } else if (alarmType.equals(AlarmType.DANGER)) {
            message = "위험 알림입니다.";
            for (Member findAllMember : findAllMembers) {
                alarmService.saveAlarm(message, alarmType, findAllMember.getId());
                sendAlarm(alarmType, findAllMember, message);
            }
        } else {
            throw new IllegalArgumentException("AlarmType not Exist");
        }
    }

    private void sendAlarm(AlarmType alarmType, Member member, String message) {
        emitterService.notify(member.getId(), AlarmDto.builder()
                .message(message)
                .alarmType(alarmType)
                .isRead(false)
                .receiver(member).build()
        );
    }
}
