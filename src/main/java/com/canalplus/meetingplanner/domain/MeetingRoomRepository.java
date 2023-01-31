package com.canalplus.meetingplanner.domain;

import com.canalplus.meetingplanner.domain.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, String>{

	@Query(value = "select * from public.rooms as ro " +
			" where ro.name not in (select re.room_name from public.reservations as re " +
									" where ro.name = re.room_name " +
									" and ((re.date_begin >= ?1  and re.date_begin < ?2 ) " +
									" or (re.date_end >= ?1 and re.date_end < ?2))) " +
			" and ?3 <=(ro.capacity * 0.7)", nativeQuery = true)
	List<MeetingRoom> findMeetingRoomAvailable(LocalDateTime db, LocalDateTime de, int members);

}
