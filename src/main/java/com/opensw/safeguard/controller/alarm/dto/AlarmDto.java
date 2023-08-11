package com.opensw.safeguard.controller.alarm.dto;

import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmDto {

    private String message; // 알림 메시지
    private Boolean isRead; // 읽음 여부
    private AlarmType alarmType;
    private Member receiver;

    @Builder
    public AlarmDto(String message, Boolean isRead, AlarmType alarmType, Member receiver) {
        this.message = message;
        this.isRead = isRead;
        this.alarmType = alarmType;
        this.receiver = receiver;
    }
}
