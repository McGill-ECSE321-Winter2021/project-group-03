package ca.mcgill.ecse321.isotopecr.dto;

import java.sql.Date;
import java.sql.Time;

public class TimeslotDto {
	private Time aTime;
	private Date aDate;
	private String aSlotID;
	
	public TimeslotDto() {
		
	}
	
	public TimeslotDto(Time aTime,Date aDate,String aId) {
		this.aDate = aDate;
		this.aTime = aTime;
		this.aSlotID = aId;
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

}
