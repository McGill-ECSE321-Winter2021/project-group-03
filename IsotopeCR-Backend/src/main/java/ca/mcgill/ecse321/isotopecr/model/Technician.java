package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Technician extends Profile{
   private Set<Service> service;
   
   @ManyToMany
   public Set<Service> getService() {
      return this.service;
   }
   
   public void setService(Set<Service> services) {
      this.service = services;
   }
   
   private Set<DailyAvailability> dailyAvailability;
   
   @OneToMany(/*mappedBy="technician" ,*/ cascade={CascadeType.ALL})
   public Set<DailyAvailability> getDailyAvailability() {
      return this.dailyAvailability;
   }
   
   public void setDailyAvailability(Set<DailyAvailability> dailyAvailabilitys) {
      this.dailyAvailability = dailyAvailabilitys;
   }
   
   }
