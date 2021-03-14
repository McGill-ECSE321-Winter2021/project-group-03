package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.isotopecr.dao.AdminRepository;
import ca.mcgill.ecse321.isotopecr.dao.AppointmentRepository;
import ca.mcgill.ecse321.isotopecr.dao.AutoRepairShopRepository;
import ca.mcgill.ecse321.isotopecr.dao.CompanyProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.CustomerRepository;
import ca.mcgill.ecse321.isotopecr.dao.DailyAvailabilityRepository;
import ca.mcgill.ecse321.isotopecr.dao.InvoiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.ProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.ResourceRepository;
import ca.mcgill.ecse321.isotopecr.dao.ServiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.TechnicianRepository;
import ca.mcgill.ecse321.isotopecr.dao.TimeslotRepository;
import ca.mcgill.ecse321.isotopecr.dao.VehicleRepository;
import ca.mcgill.ecse321.isotopecr.dto.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.service.IsotopeCRService;
import ca.mcgill.ecse321.isotopecr.service.invalidInputException;


@CrossOrigin(origins = "*")
@RestController
public class IsotopeCRRestController {
	
	@Autowired
	private IsotopeCRService service;
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
	
	
	/**
	 * @author Zichen
	 * @return List of ResourceDtos.
	 */
	@GetMapping(value = { "/resources", "/resources/" })
	public List<ResourceDto> getAllResources() {
		return service.viewAllResources().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
	}
	
	/**
	 * @author Zichen
	 * @param type The resourceType
	 * @param max The maximum availability of one resource
	 * @return A ResourceDto just created
	 */
	@PostMapping(value = { "/resource/{type}/{max}", "/resource/{type}/{max}/" })
	public ResourceDto createResource(@PathVariable("type") String type, @PathVariable("max") Integer max) 
			throws RuntimeException{
		try {
			Resource resource = service.addResource(type, max);
			return convertToDto(resource);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Zichen
	 * @return List of InvoiceDtos
	 */
//	@GetMapping(value = { "/invoice", "/invoice/" })
//	public List<InvoiceDto> getAllInvoices() {
//		return service.viewAllInvoices().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
//	}
	
	/**
	 * @author Zichen
	 * @return the total income of the system up to now
	 */
	@GetMapping(value = { "/incomesummary", "/incomesummary/" })
	public double getIncomeSummary() {
		return service.viewIncomeSummary();
	}
	
	@GetMapping(value = { "/availablities/{ID}", "/availabilities/{ID}/" })
	public List<DailyAvailabilityDto> getTechAvailabilities(@PathVariable("ID") String profileID) 
			throws IllegalArgumentException {
		Technician tech = technicianRepository.findTechnicianByProfileID(profileID);
		if (tech == null) {
			throw new IllegalArgumentException("ERROR: Input profileID not found in system.");
		}
		List <DailyAvailabilityDto> dailyAvailabilityDtos = new ArrayList<DailyAvailabilityDto>();
		for (DailyAvailability dailyavailabilities : tech.getDailyAvailability()) {
			dailyAvailabilityDtos.add(convertToDto(dailyavailabilities));
		}
		
		return dailyAvailabilityDtos;
	}
	

	/**
	 * @author Zichen
	 * @param profileID profileID stored in database
	 * @return List of DailyAvailabilityDto related to input technician
	 * @throws IllegalArgumentException
	 * @throws invalidInputException 
	 */
	@PostMapping(value = {"/appointment/{vehicle}/{service}", "/appointment/{vehicle}/{service}/"})
	public AppointmentDto bookAppointment(@PathVariable("vehicle") String licensePlate,
			@PathVariable("service") String serviceName, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") LocalTime start, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate date) 
					throws IllegalArgumentException, invalidInputException {
		
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		Customer customer = customerRepository.findCustomerByVehicle(vehicle);	
		Service serviceInSystem = serviceRepository.findServiceByServiceName(serviceName);
		
		Time startTime = Time.valueOf(start);
		Date appointmentDate = Date.valueOf(date);
		Technician technician = service.getFreeTechnician(startTime, appointmentDate);
		
		Appointment appointment = service.bookAppointment(customer, vehicle, technician, serviceInSystem, startTime, appointmentDate);
		return convertToDto(appointment);
	}
	
	@GetMapping(value = {"/pastappointment/customer/{customer}", "/pastappointment/customer/{customer}/"})
	public List<AppointmentDto> viewPastAppointmentForCustomer(@PathVariable("customer") String email ) 
					throws IllegalArgumentException, invalidInputException {
		Customer aCustomer = findProfileByEmail(email);
		List <Appointment> appointments= appointmentRepository.findAppointmentByCustomer(aCustomer);
		appointments = service.getAllAppointmentsBeforeTime(appointments);
		List <Appointment> uncancelledappointments = new ArrayList <Appointment>();
		for (Appointment appointment: appointments) {
			if(appointment.getStatus().equals(Status.BOOKED)) {
				uncancelledappointments.add(appointment);
			}
		}
		
		return convertToDto(uncancelledappointments);
	}
	
	@GetMapping(value = {"/futureappointment/customer/{customer}", "/futureappointment/customer/{customer}/"})
	public List<AppointmentDto> viewFutureAppointmentForCustomer(@PathVariable("customer") String email ) 
					throws IllegalArgumentException, invalidInputException {
		Customer aCustomer = findProfileByEmail(email);
		List <Appointment> appointments= appointmentRepository.findAppointmentByCustomer(aCustomer);
		appointments = service.getAllAppointmentsAfterTime(appointments);
		List <Appointment> uncancelledappointments = new ArrayList <Appointment>();
		for (Appointment appointment: appointments) {
			if(appointment.getStatus().equals(Status.BOOKED)) {
				uncancelledappointments.add(appointment);
			}
		}
		
		return convertToDto(uncancelledappointments);
	}
	
	@GetMapping(value = {"/pastappointment/vehicle/{vehicle}", "/pastappointment/vehicle/{vehicle}/"})
	public List<AppointmentDto> viewPastAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) 
					throws IllegalArgumentException, invalidInputException {
		
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		List <Appointment> appointments= appointmentRepository.findAppointmentByVehicle(vehicle);
		appointments = service.getAllAppointmentsBeforeTime(appointments);
		List <Appointment> uncancelledappointments = new ArrayList <Appointment>();
		for (Appointment appointment: appointments) {
			if(appointment.getStatus().equals(Status.BOOKED)) {
				uncancelledappointments.add(appointment);
			}
		}
		
		return convertToDto(uncancelledappointments);
	}
	
	@GetMapping(value = {"/futureappointment/vehicle/{vehicle}", "/futureappointment/vehicle/{vehicle}/"})
	public List<AppointmentDto> viewFutureAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) 
					throws IllegalArgumentException, invalidInputException {
		
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		List <Appointment> appointments= appointmentRepository.findAppointmentByVehicle(vehicle);
		appointments = service.getAllAppointmentsAfterTime(appointments);
		List <Appointment> uncancelledappointments = new ArrayList <Appointment>();
		for (Appointment appointment: appointments) {
			if(appointment.getStatus().equals(Status.BOOKED)) {
				uncancelledappointments.add(appointment);
			}
		}
		
		return convertToDto(uncancelledappointments);
	}


	
	/* ============================== Helpers ===============================*/
	private ResourceDto convertToDto(Resource r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Resource!");
		}
		ResourceDto resourceDto = new ResourceDto(r.getResourceType(), r.getMaxAvailable());
		return resourceDto;
	}
	
//	private InvoiceDto convertToDto(Invoice i) {
//		if (i == null) {
//			throw new IllegalArgumentException("There is no such Invoice!");
//		}
		// TODO: complete this one;
//		InvoiceDto inovoiceDto = new InvoiceDto();
//		return invoiceDto;
//	}
	
	private DailyAvailabilityDto convertToDto(DailyAvailability d) {
		if (d == null) {
			throw new IllegalArgumentException("There is no such dailyAvailability!");
		}
		DailyAvailabilityDto dailyAvailabilityDto = new DailyAvailabilityDto(d.getAvailabilityID(), d.getStartTime(), d.getEndTime(), d.getDay());
		return dailyAvailabilityDto;
	}
	
	private VehicleDto convertToDto(Vehicle v) {
		if(v==null) {
			throw new IllegalArgumentException("There is no such vehicle!");
		}
		VehicleDto vehicleDto = new VehicleDto (v.getLicensePlate(),v.getYear(),v.getModel(),v.getBrand());
		return vehicleDto;
	}
	private CustomerDto convertToDto (Customer c) {
		if(c==null) {
			throw new IllegalArgumentException("There is no such customer!");
		}
		Set<VehicleDto> vehicles = new HashSet<VehicleDto>();
		for(Vehicle v:c.getVehicle()) {
			vehicles.add(convertToDto(v));
		}
		CustomerDto customerDto = new CustomerDto (c.getFirstName(),c.getLastName(),c.getEmail(),c.getPhoneNumber(),c.getPassword(),vehicles,c.getIsRegisteredAccount());
	}
	
	// requiring service Dto
	private Service convertToDto (Service s) {
		if (s==null) {
			throw new IllegalArgumentException("There is no such service!");
		}
		
		
	}
	// requring service Dto
	private Technician convertToDto (Technician t) {
		if(t == null) {
			throw new IllegalArgumentException("There is no such technician");
		}
		
		//TechnicianDto technicianDto = new TechniciaDto();
	}
	
	private AppointmentDto convertToDto(Appointment a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such appointment!");
		}
		AppointmentDto appointmentDto = new AppointmentDto(a.getAppointmentID(), convertToDto(a.getCustomer()),convertToDto(a.getVehicle()),
				a.getTechnician(), a.getInvoice(), a.getService(), a.getTimeslot());
		return appointmentDto;
	}
	
	private List<AppointmentDto> convertToDto(List<Appointment> appointments) {
		if (appointments == null) {
			throw new IllegalArgumentException("There is no appointments.");
		}
		List<AppointmentDto> aptmtsDto = new ArrayList<AppointmentDto>();
		for(Appointment appointment:appointments) {
			aptmtsDto.add(convertToDto(appointment));
		}
		return aptmtsDto;
	}

}
