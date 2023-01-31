package com.canalplus.meetingplanner.domain;

public enum MeetingType {
    VC("VC"),
    RC("RC"),
    SPEC("SPEC"),
    RS("RS");
    private String value;

    private MeetingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
