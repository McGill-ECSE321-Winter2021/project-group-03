package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Time;

@Entity
public class DailyAvailability{
	
   public enum DayOfWeek {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday};

	
   private Technician technician;
   
   @ManyToOne(optional=false)
   public Technician getTechnician() {
      return this.technician;
   }
   
   public void setTechnician(Technician technician) {
      this.technician = technician;
   }
   
   private String availabilityID;

public void setAvailabilityID(String value) {
    this.availabilityID = value;
}
@Id
public String getAvailabilityID() {
    return this.availabilityID;
}
private DayOfWeek day;

public void setDay(DayOfWeek value) {
    this.day = value;
}
public DayOfWeek getDay() {
    return this.day;
}
private Time startTime;

public void setStartTime(Time value) {
    this.startTime = value;
}
public Time getStartTime() {
    return this.startTime;
}
private Time endTime;

public void setEndTime(Time value) {
    this.endTime = value;
}
public Time getEndTime() {
    return this.endTime;
}
}
