package ca.mcgill.ecse321.isotopecr.model;

import java.sql.Time;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Timeslot {
	private Time time;
	
	public Time getTime() {
		return this.time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	
	private Date date;
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	private String slotID;
	
	@Id
	public String getSlotID() {
		return this.slotID;
	}
	
	public void setSlotID(String slotID) {
		this.slotID = slotID;
	}
}
