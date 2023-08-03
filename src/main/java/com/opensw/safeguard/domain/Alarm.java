package com.opensw.safeguard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자
public class Alarm {

    @Id @GeneratedValue
    private Long id;

    private String message; // 알림 메시지
    private Boolean isRead; // 읽음 여부

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Builder
    public Alarm(String message, AlarmType alarmType) {
        this.message = message;
        this.alarmType = alarmType;
        this.isRead = false;
    }
}
