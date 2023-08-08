package com.opensw.safeguard.service.join;

import com.opensw.safeguard.domain.Member;
import com.opensw.safeguard.domain.dto.DuplicateUsername;
import com.opensw.safeguard.security.token.TokenInfo;

public interface MemberJoinService {
    public Member join(String memberId, String password , String email);
    public DuplicateUsername duplicateCheckUsername(String username);


}
