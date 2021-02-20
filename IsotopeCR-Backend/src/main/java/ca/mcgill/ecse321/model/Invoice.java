package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Invoice {
	
	//TODO do we need this???
	private Appointment appointment;
	
	@OneToOne
	public Appointment getAppointment(){
		return this.appointment;
	}
	
	public void setAppointment(Appointment appointment) {
		   this.appointment = appointment;
	}
	
	private double cost;
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	private String invoiceID;
	
	@Id
	public String getID() {
		return invoiceID;
	}
	
	public void setID(String invoiceID) {
		this.invoiceId = invoiceID;
	}
	
	private boolean isPaid;
	
	public boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
