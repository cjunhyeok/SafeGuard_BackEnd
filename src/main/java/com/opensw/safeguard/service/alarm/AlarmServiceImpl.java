package com.opensw.safeguard.service.alarm;

import com.opensw.safeguard.domain.Alarm;
import com.opensw.safeguard.domain.AlarmType;
import com.opensw.safeguard.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmServiceImpl implements AlarmService{

    private AlarmRepository alarmRepository;

    @Override
    @Transactional
    public Long saveAlarm(String message, AlarmType alarmType) {
        Alarm alarm = Alarm.builder()
                .message(message)
                .alarmType(alarmType)
                .build();

        Alarm savedAlarm = alarmRepository.save(alarm);

        return savedAlarm.getId();
    }
}
