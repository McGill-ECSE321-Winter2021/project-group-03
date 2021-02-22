package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class AutoRepairShop{
   private CompanyProfile companyProfile;
   
   @OneToOne(cascade={CascadeType.ALL})
   public CompanyProfile getCompanyProfile() {
      return this.companyProfile;
   }
   
   public void setCompanyProfile(CompanyProfile companyProfile) {
      this.companyProfile = companyProfile;
   }
   
   private Set<Appointment> appointment;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Appointment> getAppointment() {
      return this.appointment;
   }
   
   public void setAppointment(Set<Appointment> appointments) {
      this.appointment = appointments;
   }
   
   private Set<Resource> resource;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Resource> getResource() {
      return this.resource;
   }
   
   public void setResource(Set<Resource> resources) {
      this.resource = resources;
   }
   
   private Set<Service> service;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Service> getService() {
      return this.service;
   }
   
   public void setService(Set<Service> services) {
      this.service = services;
   }
   
   private Set<Timeslot> timeslot;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Timeslot> getTimeslot() {
      return this.timeslot;
   }
   
   public void setTimeslot(Set<Timeslot> timeslots) {
      this.timeslot = timeslots;
   }
   
   private Set<Profile> profile;
   
   @OneToMany(cascade={CascadeType.ALL})
   public Set<Profile> getProfile() {
      return this.profile;
   }
   
   public void setProfile(Set<Profile> profiles) {
      this.profile = profiles;
   }
   
   private String autoRepairShopID;

public void setAutoRepairShopID(String value) {
    this.autoRepairShopID = value;
}
@Id
public String getAutoRepairShopID() {
    return this.autoRepairShopID;
}
}
