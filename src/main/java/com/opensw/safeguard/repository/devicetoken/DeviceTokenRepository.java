package com.opensw.safeguard.repository.devicetoken;

import com.opensw.safeguard.domain.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
}
