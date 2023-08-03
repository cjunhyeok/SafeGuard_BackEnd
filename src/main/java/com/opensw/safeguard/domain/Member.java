package com.opensw.safeguard.domain;



import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 기본 생성자
@AllArgsConstructor

public class Member extends BaseTimeEntity {


    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;


    @Column(length = 255,nullable = false,unique = true,updatable = false)
    private String username;

    @Column(length = 255,nullable = false)
    private String password;

    @Column(length = 255,nullable = false,unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();




}
