package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
public class Invoice {
	
//	//TODO do we need this???
//	private Appointment appointment;
//	
//	@OneToOne
//	public Appointment getAppointment(){
//		return this.appointment;
//	}
//	
//	public void setAppointment(Appointment appointment) {
//		   this.appointment = appointment;
//	}
	
	private double cost;
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	private String invoiceID;
	
	@Id
	public String getInvoiceID() {
		return invoiceID;
	}
	
	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}
	
	private boolean isPaid;
	
	public boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
}
