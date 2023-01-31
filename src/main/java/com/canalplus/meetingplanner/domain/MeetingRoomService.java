package com.canalplus.meetingplanner.domain;

import com.canalplus.meetingplanner.domain.MeetingRoom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface MeetingRoomService {
	
	public void createMeetingRoom(MeetingRoom meetingroom);
    public List<MeetingRoom> findAll();
    public MeetingRoom findById(String name);
    public MeetingRoom update(MeetingRoom meetingroom, String name);
    public Boolean deleteMeetingRoomByName(String name);

}
