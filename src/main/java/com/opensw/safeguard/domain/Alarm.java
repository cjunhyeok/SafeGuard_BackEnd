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

    //== 연관 관계 ==//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 알람, 회원 N : 1 다대일 단방향 매핑

    @Builder
    public Alarm(String message, Boolean isRead, AlarmType alarmType, Member member) {
        this.message = message;
        this.isRead = isRead;
        this.alarmType = alarmType;
        this.member = member;
    }
}
