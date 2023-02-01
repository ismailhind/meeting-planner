package com.canalplus.meetingplanner.domain.repository;

import com.canalplus.meetingplanner.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservation, Integer>{

	@Query(value = "SELECT * FROM public.reservations WHERE room_name  = ?1", nativeQuery = true)
	List<Reservation> findByRoomName(String roomName);

}
