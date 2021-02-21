package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;


@Entity
public class DailyAvailability{
    
    private enum DayOfWeek {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday};
    private DayOfWeek day;

    public void setDay(DayOfWeek day){
        this.day = day;
    }

    public DayOfWeek getDay(){
        return this.day;
    }

    private Time startTime;

    public void setStartTime (Time startTime){
        this.startTime = startTime;
    }

    public Time getStartTime (){
        return this.startTime;
    }

    private Time endTime;

    public void setEndTime (Time endTime){
        this.endTime = endTime;
    }

    public Time getEndTime(){
        return this.endTime;
    }

    private String availabilityID;

  
    
    @Id
    public String getAvailabilityID(){
        return this.availabilityID;
    }
    public void setAvailabilityID (String availabilityID){
        this.availabilityID = availabilityID;
    }

}