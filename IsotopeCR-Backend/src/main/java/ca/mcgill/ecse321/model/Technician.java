package ca.mcgill.ecse321.model;

import java.util.*;
import java.sql.Time;
import  javax.persistence.Entity;
import  javax.persistence.OneToMany;
import  javax.persistence.Id;
import  javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("Technician")

public class Technician extends Profile
{ 
  
  private List<Service> services;
  @OneToMany(cascade= {CascadeType.ALL})
   public List<Service> getServices()
  {
    return this.services;
  }

  public void setService(List<Service> services) {
	  this.services = services;
  }

 
  
 
  private List<DailyAvailabilities> dailyAvailabilities;
  @OneToMany(cascade= {CascadeType.ALL})
  public List<DailyAvailabilities> getDailyAvailabilities()
  {
    return this.dailyAvailabilities;
  }
  
  public void setDailyAvailabilities(List<DailyAvailabilities> dailyAvailabilities) {
	  this.dailyAvailabilities=dailyAvailabilities;
  }
  
 

}


