package com.opensw.safeguard.service.alarm;


import com.opensw.safeguard.repository.alarm.EmitterRepository;
import com.opensw.safeguard.repository.alarm.MemoryEmitterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class EmitterRepositoryTest {

    private EmitterRepository emitterRepository = new MemoryEmitterRepository();
    private final Long DEFAULT_TIMEOUT = 60L * 1000L;

    @Test
    void saveTest() {
        // given
        Long id = 1L;
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        // when
        emitterRepository.save(id, sseEmitter);

        // then
        SseEmitter findEmitter = emitterRepository.findById(id);
        Assertions.assertThat(findEmitter).isEqualTo(sseEmitter);
    }
}
