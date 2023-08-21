package com.opensw.safeguard.controller.weather;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherStorage {

    private Double previousTemperature;
    private Boolean previousAlarm;

    public WeatherStorage() {
        this.previousTemperature = 0.0;
        this.previousAlarm = false;
    }

    public WeatherStorage(Double previousTemperature, Boolean previousAlarm) {
        this.previousTemperature = previousTemperature;
        this.previousAlarm = previousAlarm;
    }

    public void updatePreviousTemperature(Double previousTemperature) {
        this.previousTemperature = previousTemperature;
    }

    public void updatePreviousAlarm(Boolean previousAlarm) {
        this.previousAlarm = previousAlarm;
    }
}
