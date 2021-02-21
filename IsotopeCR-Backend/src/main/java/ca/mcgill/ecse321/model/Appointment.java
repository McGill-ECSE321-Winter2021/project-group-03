package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import java.util.List;
import java.util.Set;

@Entity
public class Appointment {
	private String appointmentID;
	
	@Id
	public String getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}
		
	private Customer customer;
	
	@ManyToOne
	public Customer getCustomer(){
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		   this.customer = customer;
	}
	
	private Vehicle vehicle;
	
	@ManyToOne
	public Vehicle getVehicle(){
		return this.vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		   this.vehicle = vehicle;
	} 
	
	private Invoice invoice;
	
	@OneToOne
	public Invoice getInvoice(){
		return this.invoice;
	}
	
	public void setInvoice(Invoice invoice) {
		   this.invoice = invoice;
	} 
	
	private Technician technician;
	
	@ManyToOne
	public Technician getTechnician(){
		return this.technician;
	}
	
	public void setTechnician(Technician technician) {
		   this.technician = technician;
	} 
	
	private Service service;
	
	@ManyToOne
	public Service getService(){
		return this.service;
	}
	
	public void setService(Service service) {
		   this.service = service;
	} 
	
	private List<Timeslot> timeslots;
	
	@ManyToMany(cascade={CascadeType.ALL})
	public List<Timeslot> getTimeslots(){
		return this.timeslots;
	}
	
	public void setTimeslots(List<Timeslot> timeslots) {
		   this.timeslots = timeslots;
	} 
}
