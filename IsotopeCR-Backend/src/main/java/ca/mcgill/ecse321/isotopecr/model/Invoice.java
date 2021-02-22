package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class Invoice{
//   private Appointment appointment;
//   
//   @OneToOne(optional=false)
//   public Appointment getAppointment() {
//      return this.appointment;
//   }
//   
//   public void setAppointment(Appointment appointment) {
//      this.appointment = appointment;
//   }
//   
   private String invoiceID;

public void setInvoiceID(String value) {
    this.invoiceID = value;
}
@Id
public String getInvoiceID() {
    return this.invoiceID;
}
private double cost;

public void setCost(double value) {
    this.cost = value;
}
public double getCost() {
    return this.cost;
}
private Boolean isPaid;

public void setIsPaid(Boolean value) {
    this.isPaid = value;
}
public Boolean getIsPaid() {
    return this.isPaid;
}
}
