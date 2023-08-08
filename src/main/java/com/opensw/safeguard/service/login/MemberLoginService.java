package com.opensw.safeguard.service.login;

import com.opensw.safeguard.security.token.TokenInfo;

public interface MemberLoginService {
    public TokenInfo login(String memberId, String password);
}
