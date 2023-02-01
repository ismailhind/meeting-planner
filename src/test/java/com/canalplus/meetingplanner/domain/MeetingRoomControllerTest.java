package com.canalplus.meetingplanner.domain;

import com.canalplus.meetingplanner.domain.controller.MeetingRoomController;
import com.canalplus.meetingplanner.domain.service.MeetingRoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingRoomController.class)
public class MeetingRoomControllerTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    MeetingRoomService meetingRoomService;

    MeetingRoom room1 = new MeetingRoom("E1001", 23, "");
    MeetingRoom room2 = new MeetingRoom("E1002", 10, "screen");
    MeetingRoom room3 = new MeetingRoom("E1003", 8, "speakerphone");
    MeetingRoom room4 = new MeetingRoom("E1004", 4, "blackboard");
    MeetingRoom room5 = new MeetingRoom("E2001", 4, "");
    MeetingRoom room6 = new MeetingRoom("E2002", 15, "screen+webcam");
    MeetingRoom room7 = new MeetingRoom("E2003", 7, "");
    MeetingRoom room8 = new MeetingRoom("E2004", 9, "blackboard");
    MeetingRoom room9 = new MeetingRoom("E3001", 13, "screen+webcam+speakerphone");
    MeetingRoom room10 = new MeetingRoom("E3002", 8, "");
    MeetingRoom room11 = new MeetingRoom("E3003", 9, "screen+speakerphone");
    MeetingRoom room12 = new MeetingRoom("E3004", 4, "blackboard");
    List<MeetingRoom> rooms = new ArrayList<>(Arrays.asList(
            room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12
    ));

    @Test
    public void getAllRooms_success() throws Exception {

        Mockito.when(meetingRoomService.findAll()).thenReturn(rooms);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)))
                .andExpect(jsonPath("$[2].name", is("E1003")));
    }

    @Test
    public void getRoomById_success() throws Exception {

        Mockito.when(meetingRoomService.findById("E3002")).thenReturn(room10);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/rooms/E3002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.name", is("E3002")));
    }

    @Test
    public void createRoom_success() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/v1/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(room1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", is("Meeting Room Created Successfully")));
    }
}