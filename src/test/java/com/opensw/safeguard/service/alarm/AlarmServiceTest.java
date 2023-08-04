package com.opensw.safeguard.service.alarm;

import com.opensw.safeguard.domain.Alarm;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.repository.alarm.AlarmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    @InjectMocks
    private AlarmServiceImpl alarmService;
    @Mock
    private AlarmRepository alarmRepository;

    @Test
    void saveAlarmTest() {
        // given
        String message = "Message Test";
        AlarmType alarmType = AlarmType.WARN;

        // stub
        when(alarmRepository.save(any(Alarm.class))).thenReturn(Alarm.builder().build());

        // when
        Long savedAlarmId = alarmService.saveAlarm(message, alarmType);

        // then
        verify(alarmRepository).save(any(Alarm.class));
    }
}