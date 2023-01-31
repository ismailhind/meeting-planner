package com.canalplus.meetingplanner.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class MeetingRoomController {

    Logger LOGGER = LoggerFactory.getLogger(MeetingRoomController.class);

    @Autowired
    private MeetingRoomService meetingRoomServiceImp;

    @RequestMapping(value = "/rooms", method = RequestMethod.GET, produces = "application/json")
    public List<MeetingRoom> getAllMeetingRooms() {
        LOGGER.info("getting all rooms!");
        return meetingRoomServiceImp.findAll();
    }

    @GetMapping(value = "/rooms/{name}", produces = "application/json")
    public ResponseEntity<MeetingRoom> getRoomById(@PathVariable("id") String name) {
        LOGGER.info("Fetching Meeting Room with name {} ", name);
        Optional<MeetingRoom> room = Optional.ofNullable(meetingRoomServiceImp.findById(name));
        return room.map(meetingRoom -> new ResponseEntity<>(meetingRoom, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/rooms", headers = "Accept=application/json")
    public ResponseEntity<String> createRoom(@RequestBody MeetingRoom meetingroom) {
        LOGGER.info("Creating Meeting Room {} ", meetingroom.getName());
        meetingRoomServiceImp.createMeetingRoom(meetingroom);
        return new ResponseEntity<String>("Meeting Room Created Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/rooms/{name}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteRoomByName(@PathVariable("name") String name) {
        Optional<MeetingRoom> room = Optional.ofNullable(meetingRoomServiceImp.findById(name));
        if (room.isEmpty()) {
            LOGGER.warn("No such meeting room with name {}", name );
            return new ResponseEntity<String>("No such meeting room", HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Creating Meeting Room {} ", room.get().getName());
        meetingRoomServiceImp.deleteMeetingRoomByName(name);
        return new ResponseEntity<String>("Meeting Room Deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/update", headers = "Accept=application/json")
    public ResponseEntity<MeetingRoom> updateRoom(@RequestBody MeetingRoom room) {
        LOGGER.info("Updating room {} ", room.getName());
        Optional<MeetingRoom> meetingRoom = Optional.ofNullable(meetingRoomServiceImp.findById(room.getName()));
        if (meetingRoom.isEmpty()) {
            LOGGER.warn("room {} not found",room.getName());
            return new ResponseEntity<MeetingRoom>(HttpStatus.NOT_FOUND);
        }
        MeetingRoom r1 = meetingRoomServiceImp.update(room, room.getName());
        LOGGER.info("room {} updated successfully",room.getName());
        return new ResponseEntity<MeetingRoom>(r1, HttpStatus.OK);
    }
}
