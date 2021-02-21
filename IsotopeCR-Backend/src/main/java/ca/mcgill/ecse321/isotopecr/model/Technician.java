package ca.mcgill.ecse321.isotopecr.model;

import  java.util.*;
import  java.sql.Time;
import  javax.persistence.Entity;
import  javax.persistence.OneToMany;
import  javax.persistence.Id;
import javax.persistence.ManyToMany;
import  javax.persistence.CascadeType;
import  javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("Technician")
public class Technician extends Profile
{
  
  private Set<Service> services;
  @ManyToMany(cascade= {CascadeType.ALL})
   public Set<Service> getServices()
  {
    return this.services;
  }

  public void setServices(Set<Service> services) {
	  this.services = services;
  }

 
  
 
  private Set<DailyAvailability> dailyAvailabilities;
  @OneToMany(cascade= {CascadeType.ALL})
  public Set<DailyAvailability> getDailyAvailabilities()
  {
    return this.dailyAvailabilities;
  }
  
  public void setDailyAvailabilities(Set<DailyAvailability> dailyAvailabilities) {
	  this.dailyAvailabilities=dailyAvailabilities;
  }
  
 

}


