package com.canalplus.meetingplanner.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.canalplus.meetingplanner.domain.Utils.isValidMeetingHour;

@RestController
@RequestMapping("/v1")
public class ReservationsController {

    Logger LOGGER = LoggerFactory.getLogger(ReservationsController.class);

    @Autowired
    private ReservationService reservationServiceImp;

    @RequestMapping(value = "/reservations", method = RequestMethod.GET, produces = "application/json")
    public List<Reservation> getAllReservations() {
        LOGGER.info("Getting all reservations");
        List<Reservation> rooms = reservationServiceImp.getReservations();
        return rooms;
    }

    @RequestMapping(value = "/rooms/{name}/reservations", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Reservation>> getAllReservationsByName(@PathVariable("name") String name) {
        LOGGER.info("Getting all reservation for room {}", name);
        List<Reservation> reservations = reservationServiceImp.findByName(name);
        if (reservations.isEmpty()) {
            LOGGER.info("no reservation found for room {}", name);
            return new ResponseEntity<List<Reservation>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
    }


    @GetMapping(value = "/reservations/{id}", produces = "application/json")
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") int id) {

        LOGGER.info("Fetching Reservation with id {}", id);
        Optional<Reservation> reservation = reservationServiceImp.findById(id);
        return reservation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/reservations/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteReservation(@PathVariable("id") int id) {
        Optional<Reservation> reservation = reservationServiceImp.findById(id);
        if (reservation.isEmpty()) {
            LOGGER.warn("No such Reservation");
            return new ResponseEntity<String>("No such Reservation", HttpStatus.NOT_FOUND);
        }
        reservationServiceImp.deleteReservationsById(id);
        LOGGER.info("Reservation with ID {} is Deleted", id);
        return new ResponseEntity<String>("Reservation Deleted", HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/reservations", headers = "Accept=application/json")
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {
        if (isValidMeetingHour(reservation.getDateBegin(), reservation.getDateEnd())) {
            LOGGER.warn("Failed to create reservation!");
            return new ResponseEntity<String>("Failed to create reservation!", HttpStatus.NO_CONTENT);
        }

        LOGGER.info("Creating Reservation {}", reservation);
        Optional<Reservation> r = reservationServiceImp.createReservation(reservation);
        if (r.isPresent()) {
            LOGGER.info("Reservation created successfully {}", reservation);
            return new ResponseEntity<String>("Reservation created successfully", HttpStatus.CREATED);
        } else {
            LOGGER.warn("Failed to create reservation!");
            return new ResponseEntity<String>("Failed to create reservation", HttpStatus.NO_CONTENT);
        }
    }
}
