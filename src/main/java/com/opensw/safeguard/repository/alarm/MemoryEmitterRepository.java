package com.opensw.safeguard.repository.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class MemoryEmitterRepository implements EmitterRepository{

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
}
