package com.canalplus.meetingplanner.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.canalplus.meetingplanner.domain.Utils.checkMeetingTypeAbility;

@Service
@Transactional
public class ReservationsServiceImp  implements ReservationService{

	@Autowired
	ReservationsRepository reservationsRepository;
	
	@Autowired
	MeetingRoomRepository meetingRoomRepository;

	public ReservationsServiceImp() {
		super();
	}

	public ReservationsServiceImp(ReservationsRepository reservationsRepository) {
		super();
		this.reservationsRepository = reservationsRepository;
	}

	@Override
	public List<Reservation> getReservations() {
		return (List<Reservation>) reservationsRepository.findAll();
	}

	@Override
	public Optional<Reservation> findById(int id) {
		 Optional<Reservation> optReservation = reservationsRepository.findById(id);
		 return optReservation;
	}

	@Override
	public Boolean deleteReservationsById(int id) {
		 Optional<Reservation> optReservation = reservationsRepository.findById(id);
		 if (optReservation.isPresent()) {
			 reservationsRepository.delete(optReservation.get());
			 return true;
		    } else {
		       throw new ResourceNotFoundException("Reservation", "Id", id);
		    }
	}

	@Override
	public List<Reservation> findByName(String name) {
		return (List<Reservation>) reservationsRepository.findByRoomName(name);
	}

	@Override
	public Optional<Reservation> createReservation(Reservation reservation) {

		List<MeetingRoom> rooms = meetingRoomRepository.findMeetingRoomAvailable(reservation.getDateBegin(),
				reservation.getDateEnd(), reservation.getParticipantsCount());

		if(!rooms.isEmpty()){
			String roomName = null;
			for (MeetingRoom room : rooms) {
				if(checkMeetingTypeAbility(room, reservation.getMeetingType())){
					roomName = room.getName();
					break;
				}
			}
			if(roomName != null){
				System.out.println("Room to reserve: " + roomName);
				reservation.setRoomName(roomName);
				System.out.println("saving reservation: " + reservation);
				reservationsRepository.save(reservation);
				return Optional.of(reservation);
			}
		}
		return Optional.empty();
	}
}
