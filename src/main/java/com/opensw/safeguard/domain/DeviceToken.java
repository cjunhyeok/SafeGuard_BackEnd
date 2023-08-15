package com.opensw.safeguard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceToken {

    @Id @GeneratedValue
    @Column(name = "device_token_id")
    private Long id;

    private String token;

    //== 연관관계 ==//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 토큰, 회원 N : 1 다대일 단방향 매핑

    @Builder
    public DeviceToken(String token, Member member) {
        this.token = token;
        this.member = member;
    }
}
