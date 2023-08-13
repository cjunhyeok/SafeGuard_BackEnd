package com.opensw.safeguard.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberJoinDTO {

    private String email;
    private String username;
    private String password;

    private String realName;

    private String phoneNumber;
    private List<String> associatedPhoneNumber;


}
