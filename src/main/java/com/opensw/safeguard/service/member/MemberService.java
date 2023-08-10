package com.opensw.safeguard.service.member;

import com.opensw.safeguard.domain.Member;

import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.security.token.TokenInfo;

import java.util.List;

public interface MemberService {

    public Member join(String memberId, String password , String email);
    public DuplicateUsername duplicateCheckUsername(String username);
    public TokenInfo login(String memberId, String password);
    List<Member> findAll();
}
