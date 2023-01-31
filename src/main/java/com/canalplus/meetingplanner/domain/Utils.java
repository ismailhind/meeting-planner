package com.canalplus.meetingplanner.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Utils {


    public static final LocalDateTime START_DAY_TIME = LocalDateTime.of(2000, 01,01,8, 00, 00);
    public static final LocalDateTime END_DAY_TIME = LocalDateTime.of(2000, 01,01,20, 00, 00);
    public static final String SPEAKERPHONE = "speakerphone";
    public static final String BLACKBOARD = "blackboard";
    public static final String SCREEN = "screen";
    public static final String WEBCAM = "webcam";

    public static boolean isValidMeetingHour(LocalDateTime beginHour, LocalDateTime endHour) {
        if(ChronoUnit.HOURS.between(beginHour, endHour) != 1) {
            //TODO
            // throw new IllegalStateException(" meeting duration interval is not allowed! try a 1 hour one!");
        return false;
        }

        if (!isInDaysHours(beginHour, endHour)){
            //TODO
            //throw new IllegalStateException(" meeting time is out of interval 8h--20h!");
            return false;
        }
        return true;
    }

    public static boolean isInDaysHours(LocalDateTime beginHour, LocalDateTime endHour) {
        return (ChronoUnit.HOURS.between(START_DAY_TIME, beginHour) >= 0
                && ChronoUnit.HOURS.between(beginHour, END_DAY_TIME) >= 0)
                && (ChronoUnit.HOURS.between(START_DAY_TIME, endHour) >= 0
                && ChronoUnit.HOURS.between(endHour, END_DAY_TIME) >= 0);
    }

    public static boolean checkMeetingTypeAbility(MeetingRoom room, String meetingType) {
        boolean possible = false;
        MeetingType type = MeetingType.valueOf(meetingType);
        switch (type){
            case RS:
                possible = (room.getCapacity() >= 3);
                break;
            case RC:
                possible = (room.getEquipment().toLowerCase().contains(SPEAKERPHONE)
                && room.getEquipment().toLowerCase().contains(BLACKBOARD)
                && room.getEquipment().toLowerCase().contains(SCREEN));
                break;
            case SPEC:
                possible = (room.getEquipment().toLowerCase().contains(BLACKBOARD));
                break;
            case VC:
                possible = (room.getEquipment().toLowerCase().contains(BLACKBOARD)
                        && room.getEquipment().toLowerCase().contains(SPEAKERPHONE)
                        && room.getEquipment().toLowerCase().contains(WEBCAM));
                break;
            default:
        }
        return possible;
    }
}