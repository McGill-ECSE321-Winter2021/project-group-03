package ca.mcgill.ecse321.isotopecr.dto;

import java.sql.Time;
import java.time.LocalTime;

import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

public class DailyAvailabilityDto {
	private String availabilityID;
	private Time startTime;
	private Time endTime;
	private DayOfWeek day;

	public DailyAvailabilityDto() {
	}

	/* Create a default DailyAvailabilityDto */
	public DailyAvailabilityDto(String availabilityID, DayOfWeek day) {
		this(availabilityID, java.sql.Time.valueOf(LocalTime.of(9, 00)), java.sql.Time.valueOf(LocalTime.of(17, 00)),
				day);
	}

	public DailyAvailabilityDto(String availabilityID, Time startTime, Time endTime, DayOfWeek day) {
		this.availabilityID = availabilityID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
	}

	public String getAvailabilityID() {
		return availabilityID;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}
}
