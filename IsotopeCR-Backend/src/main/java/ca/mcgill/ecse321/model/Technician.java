package ca.mcgill.ecse321.model;

import  java.util.*;
import  java.sql.Time;
import  javax.persistence.Entity;
import  javax.persistence.OneToMany;
import  javax.persistence.Id;
import  javax.persistence.CascadeType;
import  javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("Technician")

public class Technician extends Profile
{
  
  private Set<Service> services;
  @OneToMany(cascade= {CascadeType.ALL})
   public Set<Service> getServices()
  {
    return this.services;
  }

  public void setService(Set<Service> services) {
	  this.services = services;
  }

 
  
 
  private Set<DailyAvailabilities> dailyAvailabilities;
  @OneToMany(cascade= {CascadeType.ALL})
  public Set<DailyAvailabilities> getDailyAvailabilities()
  {
    return this.dailyAvailabilities;
  }
  
  public void setDailyAvailabilities(Set<DailyAvailabilities> dailyAvailabilities) {
	  this.dailyAvailabilities=dailyAvailabilities;
  }
  
 

}


