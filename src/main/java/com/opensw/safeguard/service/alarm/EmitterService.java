package com.opensw.safeguard.service.alarm;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterService {

    SseEmitter subscribe(Long userId);
    void notify(Long userId, Object event);
}
