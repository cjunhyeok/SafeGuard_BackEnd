package com.opensw.safeguard.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DuplicateUsername {
    private String username;
    private boolean duplicate;

}
