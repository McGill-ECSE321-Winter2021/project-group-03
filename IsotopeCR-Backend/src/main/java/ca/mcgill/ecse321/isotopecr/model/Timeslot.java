package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;
import java.sql.*;
import java.util.Set;

@Entity
public class Timeslot {
	private Time time;

	public void setTime(Time value) {
		this.time = value;
	}

	public Time getTime() {
		return this.time;
	}

	private String slotID;

	public void setSlotID(String value) {
		this.slotID = value;
	}

	@Id
	public String getSlotID() {
		return this.slotID;
	}

	private Set<Appointment> appointment;

	@ManyToMany
	public Set<Appointment> getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Set<Appointment> appointments) {
		this.appointment = appointments;
	}

	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return this.date;
	}
}
