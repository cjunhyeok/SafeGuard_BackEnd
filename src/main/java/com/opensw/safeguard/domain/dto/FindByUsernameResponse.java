package com.opensw.safeguard.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindByUsernameResponse {
    private boolean exitsMember;
    private String username;


}
