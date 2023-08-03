package com.opensw.safeguard.domain;

public enum AlarmType {

    WARN("경고"), DANGER("위험");
    private final String description;
    AlarmType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
