package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;


public class IsotopeCRService {
	@Autowired
	private CompanyProfileRepository companyProfileRepository;
	@Autowired
	private AutoRepairShopRepository autoRepairShopRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private TimeslotRepository timeslotRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	private TechnicianRepository technicianRepository;
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Transactional
	public Appointment bookAppointment(String appointmentID, Customer customer, Vehicle vehicle,
			Technician technician, Invoice invoice, ca.mcgill.ecse321.isotopecr.model.Service service, Set<Timeslot> timeslots) {
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(appointmentID);
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
	public boolean cancelAppointment (String appontmentID){
		boolean isCancelled = false;
		Appointment aptmt = appointmentRepository.findAppointmentByAppointmentID(appontmentID);
		appointmentRepository.delete(aptmt);
		
		aptmt=appointmentRepository.findAppointmentByAppointmentID(appontmentID);
		if (aptmt.equals(null)) {
			isCancelled =true;
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
    		for(Timeslot timeslot: timeslots) {
    			if (timeslot.getDate().after(curDate)||(timeslot.getDate().equals(curDate)&&timeslot.getTime().after(curTime))) {
    				isBefore = false;
    			}
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
    		for(Timeslot timeslot: timeslots) {
    			if (timeslot.getDate().after(curDate)||(timeslot.getDate().equals(curDate)&&timeslot.getTime().after(curTime))) {
    				isBefore = false;
    			}
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
