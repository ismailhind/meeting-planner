package com.canalplus.meetingplanner.domain.service;

import com.canalplus.meetingplanner.domain.Reservation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface ReservationService {
	public Optional<Reservation> createReservation(Reservation reservation);
    public List<Reservation> getReservations();
    public Optional<Reservation> findById(int id);
    public Boolean deleteReservationsById(int id);
    public List<Reservation> findByName(String name);
}
