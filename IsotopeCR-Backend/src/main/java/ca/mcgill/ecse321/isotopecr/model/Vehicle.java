package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Vehicle{
//   private Customer customer;
//   
//   @ManyToOne(optional=false)
//   public Customer getCustomer() {
//      return this.customer;
//   }
//   
//   public void setCustomer(Customer customer) {
//      this.customer = customer;
//   }
   
   private String licensePlate;

public void setLicensePlate(String value) {
    this.licensePlate = value;
}
@Id
public String getLicensePlate() {
    return this.licensePlate;
}
private Integer year;

public void setYear(Integer value) {
    this.year = value;
}
public Integer getYear() {
    return this.year;
}
private String model;

public void setModel(String value) {
    this.model = value;
}
public String getModel() {
    return this.model;
}
private String brand;

public void setBrand(String value) {
    this.brand = value;
}
public String getBrand() {
    return this.brand;
}
}
