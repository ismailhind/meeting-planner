package com.canalplus.meetingplanner.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "meeting_type")
    private String meetingType;

    @Column(name = "date_begin")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateBegin;

    @Column(name = "date_end")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateEnd;

    @Column(name = "participants")
    private int participantsCount;

    public Reservation() {
        super();
    }

    public Reservation(int id, String roomName, String meetingType, LocalDateTime dateBegin,
                       LocalDateTime dateEnd, int participantsCount) {
        super();
        this.reservationId = id;
        this.roomName = roomName;
        this.meetingType = meetingType;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.participantsCount = participantsCount;

    }

    public Reservation(String roomName, String meetingType, LocalDateTime dateBegin,
                       LocalDateTime dateEnd, int participantsCount) {
        super();
        this.roomName = roomName;
        this.meetingType = meetingType;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.participantsCount = participantsCount;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int id) {
        this.reservationId = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(LocalDateTime dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    @Override
    public String toString() {
        return "Reservations [id=" + reservationId + ", room=" + roomName + ", type=" + meetingType + ", dateBegin=" + dateBegin
                + ", dateEnd=" + dateEnd + ", participantsCount =" + participantsCount + "]";
    }
}
