package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;

import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

public class IsotopeCRService {
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	@Autowired
	AutoRepairShopRepository autoRepairShopRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	TimeslotRepository timeslotRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	VehicleRepository vehicleRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	
	@Transactional
	public void updateAvailability(Technician tech, DayOfWeek day, Time startTime, 
			Time endTime) {
		List<DailyAvailability> availabilities = toList(tech.getDailyAvailability());
		for(DailyAvailability availability : availabilities) {
			if (availability.getDay().equals(day)) {
				availability.setStartTime(startTime);
				availability.setEndTime(endTime);
				return;
			}
		}
		System.out.println("Input day is invalid, please retry.");	// TODO where to check the input
	}
	
	@Transactional
	public List<DailyAvailability> viewAvailability(Technician tech){
		List<DailyAvailability> availabilities = toList(tech.getDailyAvailability());
		return availabilities;
	}

	
	@Transactional
	public Resource addResource(String resourceType, Integer maxAvailable) {
		Resource resource = new Resource();
		resource.setResourceType(resourceType);
		resource.setMaxAvailable(maxAvailable);

		resourceRepository.save(resource);
		return resource;
	}
	
	@Transactional
	public Resource removeResource(String resourceType) {
		if (resourceRepository.existsById(resourceType)) {
			Resource resource = resourceRepository.findResourceByResourceType(resourceType);
			resourceRepository.delete(resource);
			return resource;
		} else {
			System.out.println("Input not found in the database.");
			return null;
		}
	}
	
	// return all resources
	@Transactional
	public List<Resource> viewAllResources() {
		List<Resource> resources = toList(resourceRepository.findAll());
		return resources;		
	}
	
	@Transactional
	public double viewIncomeSummary() {
		// TODO: want to see the income summation / resource allocation.
		List<Invoice> invoices = toList(invoiceRepository.findAll());
		double incomeSummary = 0d;
		for (Invoice i : invoices) {
			incomeSummary += i.getCost();
		}
		return incomeSummary;
	}
	
	@Transactional
	public Map<String, Integer> viewResourceSummary() {
		// TODO: want to see the income summation / resource allocation.
		List<Resource> resources = toList(resourceRepository.findAll());
		Map<String, Integer> resourceAllocation = new HashMap<String, Integer>();
		
		for (Resource resource : resources) {
			resourceAllocation.put(resource.getResourceType(), 0);
		}
		
		for (Appointment appointment : appointmentRepository.findAll()) {
			String type = appointment.getService().getResource().getResourceType();
			resourceAllocation.put(type, resourceAllocation.get(type) + 1);	// update the usage by 1;
		}
		
		return resourceAllocation;
	}
	
	
	


	@Transactional
	public Appointment bookAppointment(Customer customer, Vehicle vehicle,
			Technician technician, Invoice invoice, ca.mcgill.ecse321.isotopecr.model.Service service, Time startTime, Date chosenDate) {
		Appointment appointment = new Appointment();
		Integer duration = service.getDuration();
		Integer timeslotnum = duration/30;
		Set <Timeslot> timeslots = new HashSet<Timeslot>();
		
		for(int i=0;i<timeslotnum;i++) {
		       Timeslot ts = new Timeslot();
		       ts.setDate(chosenDate);
		       ts.setTime(startTime);
		       ts.setSlotID(String.valueOf(chosenDate)+String.valueOf(startTime));
		       timeslots.add(ts);
		       LocalTime localtime = startTime.toLocalTime();
		       localtime = localtime.plusMinutes(30);
		       startTime = Time.valueOf(localtime);
		}
		
		Timeslot timeslot = timeslots.iterator().next();
		appointment.setAppointmentID(String.valueOf(customer.getProfileID().hashCode()*vehicle.getLicensePlate().hashCode()*timeslot.getSlotID().hashCode()));
		appointment.setCustomer(customer);
		appointment.setVehicle(vehicle);
		appointment.setTechnician(technician);
		appointment.setInvoice(invoice);
		appointment.setService(service);
		appointment.setTimeslot(timeslots);
		
		appointmentRepository.save(appointment);
		
		return appointment;
	}
	
	@Transactional
	public boolean cancelAppointment (String appointmentID){
		boolean isCancelled = false;
		if(appointmentRepository.existsById(appointmentID)) {
		    Appointment aptmt = appointmentRepository.findAppointmentByAppointmentID(appointmentID);
		    appointmentRepository.delete(aptmt);
		
		    aptmt=appointmentRepository.findAppointmentByAppointmentID(appointmentID);
		    if (aptmt.equals(null)) {
			    isCancelled =true;
		    }
		}else {
			System.out.println("Invalid appointment ID");
		}
		return isCancelled;
	}
	
	// do we actually need this? Or only the two getting
	@Transactional
	public List<Appointment> getAllAppointments(){
		return toList(appointmentRepository.findAll());
	}
	
    @Transactional 
    public List<Appointment> getAllAppointmentsBeforeTime(List<Appointment> appointments){
    	Date curDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
    	Time curTime = new java.sql.Time(Calendar.getInstance().getTimeInMillis());
    	List<Appointment> aptmtBeforeTime = new ArrayList<Appointment>();
    	for(Appointment aptmt :appointments) {
    		boolean isBefore = true;
    		
    		Set<Timeslot> timeslots = aptmt.getTimeslot();
    		Timeslot timeslot = timeslots.iterator().next();

    		if (timeslot.getDate().after(curDate)||(timeslot.getDate().equals(curDate)&&timeslot.getTime().after(curTime))) {
    				isBefore = false;
    		}
    		
    		
    		if(isBefore ==true)
    			aptmtBeforeTime.add(aptmt);
    	}
    	
    	return aptmtBeforeTime;
    }
    
    @Transactional
    public List<Appointment> getAllAppointmentsAfterTime(List<Appointment> appointments){
    	Date curDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
    	Time curTime = new java.sql.Time(Calendar.getInstance().getTimeInMillis());
    	List<Appointment> aptmtBeforeTime = new ArrayList<Appointment>();
    	for(Appointment aptmt :appointments) {
    		boolean isBefore = true;
    		
    		Set<Timeslot> timeslots = aptmt.getTimeslot();
    		Timeslot timeslot = timeslots.iterator().next();

    		if (timeslot.getDate().after(curDate)||(timeslot.getDate().equals(curDate)&&timeslot.getTime().after(curTime))) {
    				isBefore = false;
    		}
    		
    		if(isBefore ==false)
    			aptmtBeforeTime.add(aptmt);
    	}
    	
    	return aptmtBeforeTime;
    }
    
    @Transactional
    public List<Appointment> getAppointmentsByCustomer(Customer aCustomer){
    	List<Appointment> allAppointmentByPerson = appointmentRepository.findAppointmentByCustomer(aCustomer);
    	return allAppointmentByPerson;
    }
    
    @Transactional
    public List<Appointment> getAppointmentsByTechnician(Technician technician){
    	List<Appointment> allAppointmentByTechnician= appointmentRepository.findAppointmentByTechnician(technician);
    	return allAppointmentByTechnician;
    }
    
    @Transactional
    public List<Appointment> getAppointmentsByVehicle(Vehicle vehicle){
    	List<Appointment> allAppointmentByVehicle= appointmentRepository.findAppointmentByVehicle(vehicle);
    	return allAppointmentByVehicle;
    }
    
    @Transactional
    public List<Appointment> getAppointmentsByService(Service service){
    	List<Appointment> allAppointmentByService= appointmentRepository.findAppointmentByService((ca.mcgill.ecse321.isotopecr.model.Service) service);
    	return allAppointmentByService;
    }

    

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}

