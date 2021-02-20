package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import java.util.Set;

@Entity
@DiscriminatorValue("Customer")
public class Customer extends Profile{
    private String phoneNumber;

    public void setPhoneNumber (String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber (){
        return this.phoneNumber;
    }

    private Set<Vehicle> vehicles;
    @OneToMany(cascade={CascadeType.ALL})
    public void setVehicles (Set<Vehicle> vehicles){
        this.vehicles = vehicles;
    }

    public Set<Vehicle> getVehicles (){
        return this.vehicles;
    }
}
