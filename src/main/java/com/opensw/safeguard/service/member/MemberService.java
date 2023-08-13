package com.opensw.safeguard.service.member;

import com.opensw.safeguard.domain.Member;

import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.security.token.TokenInfo;

import java.util.List;

public interface MemberService {

    Member join(String memberId, String password , String email,String realName, String phoneNumber,List<String> associatedPhoneNumber);
    DuplicateUsername duplicateCheckUsername(String username);
    TokenInfo login(String memberId, String password);
    List<Member> findAll();
    Member findByUsername(String username);
}
