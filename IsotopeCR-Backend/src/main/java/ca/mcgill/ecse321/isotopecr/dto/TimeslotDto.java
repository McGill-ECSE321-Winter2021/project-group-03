package ca.mcgill.ecse321.isotopecr.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import ca.mcgill.ecse321.isotopecr.model.Appointment;

public class TimeslotDto {
	private Time aTime;
	private Date aDate;
	private String aSlotID;
	private Set<Appointment> aAppointment;

	public TimeslotDto() {

	}

	public TimeslotDto(Time aTime, Date aDate, String aId) {
		this.aDate = aDate;
		this.aTime = aTime;
		this.aSlotID = aId;
	}

	public TimeslotDto(Time aTime, Date aDate, String aId, Set<Appointment> appointment) {
		this.aDate = aDate;
		this.aTime = aTime;
		this.aSlotID = aId;
		this.aAppointment = appointment;
	}

	public Time getTime() {
		return this.aTime;
	}

	public Date getDate() {
		return this.aDate;
	}

	public String getSlotID() {
		return this.aSlotID;
	}

	public Set<Appointment> getAppointments() {
		return this.aAppointment;
	}

	public void setAppointments(Set<Appointment> appointment) {
		this.aAppointment = appointment;
	}

}
