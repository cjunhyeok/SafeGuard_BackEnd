package com.opensw.safeguard.security.service;

import com.opensw.safeguard.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;


@Getter

public class MemberAdapter extends User {
    private Member member;


    public MemberAdapter(Member member,PasswordEncoder passwordEncoder) {
        super(member.getUsername(), passwordEncoder.encode(member.getPassword()), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }


    public MemberAdapter(String subject, String s, Collection<? extends GrantedAuthority> authorities) {
        super(subject, s, authorities);
    }
}
