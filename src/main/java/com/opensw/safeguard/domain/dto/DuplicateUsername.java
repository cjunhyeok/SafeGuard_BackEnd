package com.opensw.safeguard.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateUsername {
    private String username;
    private boolean duplicate;

}
