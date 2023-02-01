package com.canalplus.meetingplanner.domain;

import com.canalplus.meetingplanner.domain.controller.ReservationsController;
import com.canalplus.meetingplanner.domain.service.MeetingRoomService;
import com.canalplus.meetingplanner.domain.service.ReservationService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationsController.class)
class ReservationsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ReservationService reservationService;

    @MockBean
    MeetingRoomService meetingRoomService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Reservation reservation1 = new Reservation(1,"E1001","RS", LocalDateTime.parse("2023-01-30 13:00:00",formatter),
            LocalDateTime.parse("2023-01-30 14:00:00",formatter),3);

    Reservation reservation2 = new Reservation(2,"E1001","RS", LocalDateTime.parse("2023-01-30 15:00:00",formatter),
            LocalDateTime.parse("2023-01-30 16:00:00",formatter),4);

    Reservation reservation3 = new Reservation(1,"E2001","RC", LocalDateTime.parse("2023-01-30 08:00:00",formatter),
            LocalDateTime.parse("2023-01-30 09:00:00",formatter),5);

    Reservation reservation4 = new Reservation(1,"E3001","SPEC", LocalDateTime.parse("2023-01-30 13:00:00",formatter),
            LocalDateTime.parse("2023-01-30 14:00:00",formatter),9);
    List<Reservation> reservations = new ArrayList<>(Arrays.asList(reservation1, reservation2, reservation3, reservation4));
    List<Reservation> reservationsForRoom1 = new ArrayList<>(Arrays.asList(reservation1, reservation2));

    @Test
    public void getAllReservations_success() throws Exception {

        Mockito.when(reservationService.getReservations()).thenReturn(reservations);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[2].roomName", is("E2001")));
    }

    @Test
    public void getReservationByRoomName_success() throws Exception {

        Mockito.when(reservationService.findByName("E1001")).thenReturn(reservationsForRoom1);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/rooms/E1001/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roomName", is("E1001")))
                .andExpect(jsonPath("$[0].reservationId", is(1)));
    }

    @Test
    public void getReservationByRoomName_Failed() throws Exception {

        Mockito.when(reservationService.findByName("E1001")).thenReturn(Collections.emptyList());


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/rooms/E1001/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void createReservation_failed() throws Exception {
        Reservation reservation5 = new Reservation(1,"E3001","SPEC", LocalDateTime.parse("2023-01-30 23:00:00",formatter),
                LocalDateTime.parse("2023-01-30 00:00:00",formatter),9);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/v1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(reservation5));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", is("Failed to create reservation")));
    }
}