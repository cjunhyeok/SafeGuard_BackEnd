package com.opensw.safeguard.controller.alarm;

import com.opensw.safeguard.controller.alarm.dto.AlarmDto;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.DeviceToken;
import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.service.alarm.AlarmService;
import com.opensw.safeguard.service.alarm.EmitterService;
import com.opensw.safeguard.service.alarm.FirebaseCloudMessageService;
import com.opensw.safeguard.service.devicetoken.DeviceTokenService;
import com.opensw.safeguard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AlarmApiController {

    private final AlarmService alarmService;
    private final EmitterService emitterService;
    private final MemberService memberService;
    private final DeviceTokenService deviceTokenService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping(value = "/api/alarm/{alarmType}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void alarm(@PathVariable(value = "alarmType")AlarmType alarmType) {

        List<Member> findAllMembers = memberService.findAll();
        if (findAllMembers.isEmpty()) {
            return ;
        }
        String message = makeMessage(alarmType);

        for (Member findAllMember : findAllMembers) {
            alarmService.saveAlarm(message, alarmType, findAllMember.getId());
            emitterService.notify(findAllMember.getId(), AlarmDto.builder()
                    .message(message)
                    .alarmType(alarmType)
                    .isRead(false)
                    .receiver(findAllMember).build()
            );
        }
    }

    @PostMapping("/api/alarm/{alarmType}")
    public void firebaseAlarm(@PathVariable(value = "alarmType")AlarmType alarmType) throws IOException {
        List<DeviceToken> findAllDeviceToken = deviceTokenService.findAll();

        String message = makeMessage(alarmType);

        for (DeviceToken deviceToken : findAllDeviceToken) {
            alarmService.saveAlarm(message, alarmType, deviceToken.getMember().getId());
            firebaseCloudMessageService.sendMessageTo(deviceToken.getToken(), message, message);
        }
    }

    private String makeMessage(AlarmType alarmType) {
        if (alarmType.equals(AlarmType.WARN)) {
            return "경고 알림입니다.";
        } else if (alarmType.equals(AlarmType.DANGER)) {
            return "위험 알림입니다.";
        } else {
            throw new IllegalArgumentException("AlarmType not Exist");
        }
    }
}
