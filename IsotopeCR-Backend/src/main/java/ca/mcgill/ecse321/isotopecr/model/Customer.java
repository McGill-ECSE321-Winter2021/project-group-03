package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends Profile{
   private Set<Vehicle> vehicle;
   
   @OneToMany(mappedBy="customer" , cascade={CascadeType.ALL})
   public Set<Vehicle> getVehicle() {
      return this.vehicle;
   }
   
   public void setVehicle(Set<Vehicle> vehicles) {
      this.vehicle = vehicles;
   }
   
   private String phoneNumber;

public void setPhoneNumber(String value) {
    this.phoneNumber = value;
}
public String getPhoneNumber() {
    return this.phoneNumber;
}
}
