package com.canalplus.meetingplanner.domain.service;

import com.canalplus.meetingplanner.domain.MeetingRoom;
import com.canalplus.meetingplanner.domain.repository.MeetingRoomRepository;
import com.canalplus.meetingplanner.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MeetingRoomServiceImp implements MeetingRoomService {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomServiceImp(MeetingRoomRepository meetingRoomRepository) {
        super();
        this.meetingRoomRepository = meetingRoomRepository;
    }


    public MeetingRoomServiceImp() {
        super();
    }


    @Override
    public void createMeetingRoom(MeetingRoom meetingroom) {
        meetingRoomRepository.save(meetingroom);
    }

    @Override
    public List<MeetingRoom> findAll() {
        return (List<MeetingRoom>) meetingRoomRepository.findAll();
    }

    @Override
    public MeetingRoom findById(String name) {
        Optional<MeetingRoom> optMeetingRoom = meetingRoomRepository.findById(name);
        if (optMeetingRoom.isPresent()) {
            return optMeetingRoom.get();
        } else {
            throw new ResourceNotFoundException("room", "Name", name);
        }
    }

    @Override
    public MeetingRoom update(MeetingRoom meetingroom, String name) {
        Optional<MeetingRoom> optMeetingRoom = meetingRoomRepository.findById(name);
        if (optMeetingRoom.isPresent()) {
            MeetingRoom newRoom = optMeetingRoom.get();
            newRoom.setName(meetingroom.getName());
            newRoom.setCapacity(meetingroom.getCapacity());
            return meetingRoomRepository.save(newRoom);
        } else {
            throw new ResourceNotFoundException("room", "Name", name);
        }
    }

    @Override
    public Boolean deleteMeetingRoomByName(String name) {
        Optional<MeetingRoom> room = meetingRoomRepository.findById(name);
        if (room.isPresent()) {
            meetingRoomRepository.delete(room.get());
            return true;
        } else {
            throw new ResourceNotFoundException("room", "name", name);
        }
    }
}
