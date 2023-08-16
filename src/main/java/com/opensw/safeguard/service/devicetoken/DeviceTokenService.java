package com.opensw.safeguard.service.devicetoken;

import com.opensw.safeguard.domain.DeviceToken;

import java.util.List;

public interface DeviceTokenService {

    Long save(String deviceToken, Long memberId);
    List<DeviceToken> findAll();
}
