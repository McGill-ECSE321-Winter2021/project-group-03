package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Set;


public class AppointmentDto {
	private CustomerDto aCustomer;
	private VehicleDto  aVehicle;
	private TechnicianDto aTechnician;
	private InvoiceDto aInvoice;
	private ServiceDto aService;
	private Set<TimeslotDto> timeslots;
	private String aAppointmentID;
	public AppointmentDto() {
	}
	
	public AppointmentDto (String appointmentID, CustomerDto customer, VehicleDto vehicle,
			TechnicianDto technician, InvoiceDto invoice, ServiceDto service, Set<TimeslotDto> timeslots) {
		this.aAppointmentID=appointmentID;
		this.aCustomer = customer;
		this.aVehicle = vehicle;
		this.aTechnician = technician;
		this.aInvoice = invoice;
		this.aService = service;
		this.timeslots = timeslots;
	}
	
	public String getAppointmentID() {
		return this.aAppointmentID;
	}
	
	public CustomerDto getCustomer() {
		return this.aCustomer;
	}
	public void setCustomer(CustomerDto customer) {
		this.aCustomer = customer;
	}
	
	public VehicleDto getvehicle() {
		return this.aVehicle;
	}
	public void setvehicle(VehicleDto vehicle) {
		this.aVehicle = vehicle;
	}
	
	public TechnicianDto getTechnician() {
		return this.aTechnician;
	}
	public void setTechnician(TechnicianDto technician) {
		this.aTechnician = technician;
	}
	
	public InvoiceDto getInvoice() {
		return this.aInvoice;
	}
	public void setInvoice(InvoiceDto invoice) {
		this.aInvoice = invoice;
	}
	
	public ServiceDto getService() {
		return this.aService;
	}
	public void setService(ServiceDto service) {
		this.aService = service;
	}
	
	public Set<TimeslotDto> getTimeslots(){
		return this.timeslots;
	}
	public void setTimeslots(Set<TimeslotDto> timeslots){
		this.timeslots = timeslots;
	}
}
