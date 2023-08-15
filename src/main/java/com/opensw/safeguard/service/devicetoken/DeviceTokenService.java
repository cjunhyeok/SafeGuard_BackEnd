package com.opensw.safeguard.service.devicetoken;

public interface DeviceTokenService {

    Long save(String deviceToken, Long memberId);
}
