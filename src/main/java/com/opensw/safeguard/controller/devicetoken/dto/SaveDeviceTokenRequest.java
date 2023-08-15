package com.opensw.safeguard.controller.devicetoken.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveDeviceTokenRequest {

    private String deviceToken;

    @Builder
    public SaveDeviceTokenRequest(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}