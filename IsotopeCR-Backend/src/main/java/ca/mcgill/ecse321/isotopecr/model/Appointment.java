package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class Appointment{
   private Customer customer;
   
   @ManyToOne(optional=false)
   public Customer getCustomer() {
      return this.customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   private Vehicle vehicle;
   
   @ManyToOne(optional=false)
   public Vehicle getVehicle() {
      return this.vehicle;
   }
   
   public void setVehicle(Vehicle vehicle) {
      this.vehicle = vehicle;
   }
   
   private Technician technician;
   
   @ManyToOne(optional=false)
   public Technician getTechnician() {
      return this.technician;
   }
   
   public void setTechnician(Technician technician) {
      this.technician = technician;
   }
   
   private Invoice invoice;
   
   @OneToOne(/*mappedBy="appointment",*/ cascade={CascadeType.ALL})
   public Invoice getInvoice() {
      return this.invoice;
   }
   
   public void setInvoice(Invoice invoice) {
      this.invoice = invoice;
   }
   
   private Service service;
   
   @ManyToOne(optional=false)
   public Service getService() {
      return this.service;
   }
   
   public void setService(Service service) {
      this.service = service;
   }
   
   private Set<Timeslot> timeslot;
   
   @ManyToMany(mappedBy="appointment" )
   public Set<Timeslot> getTimeslot() {
      return this.timeslot;
   }
   
   public void setTimeslot(Set<Timeslot> timeslots) {
      this.timeslot = timeslots;
   }
   
   private String appointmentID;

public void setAppointmentID(String value) {
    this.appointmentID = value;
}
@Id
public String getAppointmentID() {
    return this.appointmentID;
}
}
