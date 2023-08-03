package com.opensw.safeguard.service.alarm;

import com.opensw.safeguard.domain.AlarmType;

public interface AlarmService {

    Long saveAlarm(String message, AlarmType alarmType);
}
