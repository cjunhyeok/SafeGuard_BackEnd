package com.opensw.safeguard.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthCode {
    private String authCode;

}

