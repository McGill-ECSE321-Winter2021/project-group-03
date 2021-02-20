package ca.mcgill.ecse321.model;

import java.util.*;
import java.sql.Time;
import  javax.persistence.Entity;
import  java.util.*;
import  javax.persistence.OneToMany;
import  javax.persistence.Id;
import  javax.persistence.CascadeType;

@Entity
public class Technician extends Profile
{

 


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Technician(String aFirstName, String aLastName, String aEmail, String aPassword, boolean aIsRegisteredAccount, AutoRepairShop aAutoRepairShop)
  {
    super(aFirstName, aLastName, aEmail, aPassword, aIsRegisteredAccount, aAutoRepairShop);
    services = new ArrayList<Service>();
    dailyAvailabilities = new ArrayList<DailyAvailabilities>();
  }

  
  
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


