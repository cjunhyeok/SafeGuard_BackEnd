package com.opensw.safeguard.service.alarm;


import com.opensw.safeguard.repository.alarm.EmitterRepository;
import com.opensw.safeguard.repository.alarm.MemoryEmitterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        assertThat(findEmitter).isEqualTo(sseEmitter);
    }

    @Test
    void deleteByIdTest() {
        // given
        Long id = 1L;
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, sseEmitter);

        // when
        emitterRepository.deleteById(id);

        // then
        SseEmitter findSseEmitter = emitterRepository.findById(id);
        assertThat(findSseEmitter).isNull();
    }

    @Test
    void findAllTest() {
        // given
        Long id = 1L;
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id, sseEmitter);
        Long id2 = 2L;
        SseEmitter sseEmitter2 = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(id2, sseEmitter2);

        // when
        List<SseEmitter> sseEmitters = emitterRepository.findAll();

        // then
        assertThat(sseEmitters.size()).isEqualTo(2);
        assertThat(sseEmitters.get(0)).isEqualTo(emitterRepository.findById(id));
        assertThat(sseEmitters.get(1)).isEqualTo(emitterRepository.findById(id2));
    }
}
