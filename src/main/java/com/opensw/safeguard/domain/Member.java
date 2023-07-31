package com.opensw.safeguard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자
public class Member extends BaseTimeEntity{

    // 회원 정보 저장을 위한 엔티티

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username; // 사용자 Id
    private String password; // 사용자 password
    private String role; // 사용자 권한
    private String email; // 사용자 이메일 (이메일 인증을 위한 이메일)

    //== 생성 메서드 ==//
    @Builder // 빌더 패턴을 이용한 생성자 (id 필드 제외)
    public Member(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}
