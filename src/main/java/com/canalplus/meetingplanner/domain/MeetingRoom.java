package com.canalplus.meetingplanner.domain;


import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name="rooms")
public class MeetingRoom {

	@Id
	@Column(name="name")
	private String name;
	
	@Column(name="capacity")
	@NonNull
	private int capacity;

	@Column(name="equipment")
	private String equipment;

	public MeetingRoom() {
		super();
	}

	public MeetingRoom(String name, int capacity, String equipment) {
		super();
		this.name = name;
		this.capacity = capacity;
		this.equipment = equipment;
	}

	public MeetingRoom(int capacity, String equipment) {
		super();
		this.capacity = capacity;
		this.equipment = equipment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
}
