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
import javax.servlet.http.HttpSession;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.dto.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.service.IsotopeCRService;
import ca.mcgill.ecse321.isotopecr.service.InvalidInputException;

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
	 * @param max  The maximum availability of one resource
	 * @return A ResourceDto just created
	 */
	@PostMapping(value = { "/resource/{type}/{max}", "/resource/{type}/{max}/" })
	public ResourceDto createResource(@PathVariable("type") String type, @PathVariable("max") Integer max)
			throws RuntimeException {
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
		List<DailyAvailabilityDto> dailyAvailabilityDtos = new ArrayList<DailyAvailabilityDto>();
		for (DailyAvailability dailyavailabilities : tech.getDailyAvailability()) {
			dailyAvailabilityDtos.add(convertToDto(dailyavailabilities));
		}

		return dailyAvailabilityDtos;
	}
	
	/* ----------------------- Jack Wei --------------------------*/
	@PostMapping(value = { "/create-customer-profile", "/create-customer-profile/"})
	public CustomerDto createCustomerProfile(@RequestParam("email") String email, 
											 @RequestParam("firstName") String firstName,
											 @RequestParam("lastName") String lastName,
											 @RequestParam("phoneNumber") String phoneNumber,
											 @RequestParam("password") String password)
	throws IllegalArgumentException, InvalidInputException {
		try {
			Customer customer = service.createCustomerProfile(firstName, lastName, email, phoneNumber, password);
			return convertToDto(customer);
		} catch (InvalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/create-admin-profile", "/create-admin-profile/"})
	public AdminDto createAdminProfile(@RequestParam("email") String email, 
											 @RequestParam("firstName") String firstName,
											 @RequestParam("lastName") String lastName,
											 @RequestParam("password") String password,
											 @RequestParam("isOwner") boolean isOwner)
	throws IllegalArgumentException, InvalidInputException {
		try {
			Admin admin = service.createAdminProfile(firstName, lastName, email, isOwner, password);
			return convertToDto(admin);
		} catch (InvalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/create-technician-profile", "/create-technician-profile/"})
	public TechnicianDto createTechnicianProfile(@RequestParam("email") String email, 
											 @RequestParam("firstName") String firstName,
											 @RequestParam("lastName") String lastName,
											 @RequestParam("password") String password)
	throws IllegalArgumentException, InvalidInputException {
		try {
			Technician technician = service.createTechnicianProfile(firstName, lastName, email, password);
			return convertToDto(technician);
		} catch (InvalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/delete-profile/{email}", "/delete-profile/{email}/"})
	public ProfileDto deleteProfile(@PathVariable("email") String email) throws IllegalArgumentException, InvalidInputException {
		try {
			Profile profile = service.getProfile(email);
			service.deleteProfile(profile);
			return convertToDto(profile);
		} catch (InvalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "customer/add-vehicle/{email}", "customer/add-vehicle/{email}/"})
	public VehicleDto addVehicle(@PathVariable("email") String email,
								 @RequestParam("licensePlate") String licensePlate,
								 @RequestParam("year") String year,
								 @RequestParam("model") String model,
								 @RequestParam("brand") String brand) throws Exception {
		try {
			Profile profile = service.getProfile(email);
			if(profile instanceof Customer){
				Vehicle vehicle = service.addVehicle((Customer)profile, licensePlate, year, model, brand);
				return convertToDto(vehicle);
			} else {
				throw new InvalidInputException(); //TODO
			}
		} catch (Exception e) {
			throw e;// TODO: Exception
		}
	}
	
	@PostMapping(value = { "customer/delete-vehicle/", "customer/delete-vehicle/"})
	public VehicleDto deleteVehicle(@RequestParam("email") String email,
								 	@RequestParam("licensePlate") String licensePlate) throws Exception {
		try {
			Profile profile = service.getProfile(email);
			if(profile instanceof Customer){
				Vehicle vehicle = service.deleteVehicle((Customer)profile, licensePlate);
				return convertToDto(vehicle);
			} else {
				throw new InvalidInputException(); //TODO
			}
		} catch (Exception e) {
			throw e;// TODO: Exception
		}
	}
	
	@PostMapping(value = { "/edit-password", "/edit-password/"})
	public ProfileDto editPassword(@RequestParam("email") String email,
								   @RequestParam("password") String password) throws Exception {
		try {
			Profile profile = service.getProfile(email);
			profile = service.editPassword(profile, password);
			return convertToDto(profile);
		} catch (Exception e) {
			throw e;// TODO: Exception
		}
	}
	
	@PostMapping(value = { "/edit-password", "/edit-password/"})
	public CustomerDto editPhoneNumber(@RequestParam("email") String email,
								   	   @RequestParam("phoneNumber") String phoneNumber) throws Exception {
		try {
			Profile profile = service.getProfile(email);
			if(profile instanceof Customer) {
				Customer customer = service.editPhoneNumber(profile, phoneNumber);
				return convertToDto(customer);
			} else {
				throw new Exception(); //TODO: excpetion
			}
		} catch (Exception e) {
			throw e;// TODO: Exception
		}
	}
	
	@PostMapping(value = { "technician/add-service/{email}", "customer/add-service/{email}/"})
	public ServiceDto addServiceToTechnician(@PathVariable("email") String email,
								 @RequestParam("serviceName") String serviceName) throws Exception {
		try {
			Profile profile = service.getProfile(email);
			if(profile instanceof Customer){
				ca.mcgill.ecse321.isotopecr.model.Service technicianService = service.addServiceToProfile((Technician)profile, serviceName);
				return convertToDto(technicianService);
			} else {
				throw new InvalidInputException(); //TODO
			}
		} catch (Exception e) {
			throw e;// TODO: Exception
		}
	}
	
	@PostMapping(value = { "/login", "/login/"})
	public Profile login(@RequestParam("email") String email, 
							@RequestParam("password") String password,
							HttpSession session)
	throws IllegalArgumentException, InvalidInputException {
		try {
			Profile profile = service.getProfile(email);
			if (profile.getIsRegisteredAccount()) {
				if (password != null && profile.getPassword().equals(password)) {
					session.setAttribute(email, profile);
				} else {
					throw new InvalidInputException(); // TODO: Needs specific exception
				}
			} else {
				throw new InvalidInputException(); // TODO: Needs specific exception
			}
			return profile;
		} catch (Exception e) {
			throw e;
		}
	}

	@PostMapping(value = { "/logout", "/logout/" })
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "logout sucessfully";
	}

	/**
	 * @author Zichen
	 * @param profileID profileID stored in database
	 * @return List of DailyAvailabilityDto related to input technician
	 * @throws IllegalArgumentException
	 * @throws InvalidInputException 
	 */

	@PostMapping(value = { "/appointment/{vehicle}/{service}", "/appointment/{vehicle}/{service}" })
	public AppointmentDto bookAppointment(@PathVariable("vehicle") String licensePlate,

			@PathVariable("service") String serviceName, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") LocalTime start, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate date) 
					throws IllegalArgumentException, InvalidInputException {
		
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		Customer customer = customerRepository.findCustomerByVehicle(vehicle);
		Service serviceInSystem = serviceRepository.findServiceByServiceName(serviceName);

		Time startTime = Time.valueOf(start);
		Date appointmentDate = Date.valueOf(date);
		Technician technician = service.getFreeTechnician(startTime, appointmentDate);

		Appointment appointment = service.bookAppointment(customer, vehicle, technician, serviceInSystem, startTime,
				appointmentDate);
		return convertToDto(appointment);
	}

	@GetMapping(value = { "/pastappointment/customer/{customer}", "/pastappointment/customer/{customer}/" })
	public List<AppointmentDto> viewPastAppointmentForCustomer(@PathVariable("customer") String email)
			throws Exception {
		try {
			Profile aCustomer = service.getProfile(email);
			if (!(aCustomer instanceof Customer)) {
				throw new IllegalArgumentException("This is not a customer account.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByCustomer((Customer) aCustomer);

			appointments = service.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/futureappointment/customer/{customer}", "/futureappointment/customer/{customer}/" })
	public List<AppointmentDto> viewFutureAppointmentForCustomer(@PathVariable("customer") String email)
			throws Exception {
		try {
			Profile aCustomer = service.getProfile(email);
			if (!(aCustomer instanceof Customer)) {
				throw new IllegalArgumentException("This is not a customer account.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByCustomer((Customer) aCustomer);

			appointments = service.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/pastappointment/vehicle/{vehicle}", "/pastappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewPastAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) {
		try {
			Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
			if (vehicle == null) {
				throw new IllegalArgumentException("There is no such vehicle in the system.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByVehicle(vehicle);

			appointments = service.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/futureappointment/vehicle/{vehicle}", "/futureappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewFutureAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) {
		try {
			Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
			if (vehicle == null) {
				throw new IllegalArgumentException("There is no such vehicle in the system.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByVehicle(vehicle);

			appointments = service.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/cancelappointment/{appointment}", "/cancelappointment/{appointment}/" })
	public AppointmentDto cancelAppointment(@PathVariable("appointment") String aAppointmentId) {
		try {
			Appointment appointment = appointmentRepository.findAppointmentByAppointmentID(aAppointmentId);

			appointment = service.cancelAppointment(appointment);

			return convertToDto(appointment);
		} catch (Exception e) {
			throw e;
		}
	}

	/* ============================== Helpers =============================== */
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
	// InvoiceDto inovoiceDto = new InvoiceDto();
	// return invoiceDto;
//	}

	private DailyAvailabilityDto convertToDto(DailyAvailability d) {
		if (d == null) {
			throw new IllegalArgumentException("There is no such dailyAvailability!");
		}
		DailyAvailabilityDto dailyAvailabilityDto = new DailyAvailabilityDto(d.getAvailabilityID(), d.getStartTime(),
				d.getEndTime(), d.getDay());
		return dailyAvailabilityDto;
	}

	private CustomerDto convertToDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer profile does not exist.");
		}
		CustomerDto customerDto = new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
				customer.getPhoneNumber());
		return customerDto;
	}

	private VehicleDto convertToDto(Vehicle v) {
		if (v == null) {
			throw new IllegalArgumentException("There is no such vehicle!");
		}
		VehicleDto vehicleDto = new VehicleDto(v.getLicensePlate(), v.getYear(), v.getModel(), v.getBrand());
		return vehicleDto;
	}

	// requiring service Dto
	private Service convertToDto(Service s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such service!");
		}

	}

	private AdminDto convertToDto(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("Administrative account does not exist.");
		}
		AdminDto adminDto = new AdminDto(admin.getFirstName(), admin.getLastName(), admin.getEmail(), admin.getIsOwner());
		return adminDto;
	}

	private TechnicianDto convertToDto(Technician technician) {
		if (technician == null) {
			throw new IllegalArgumentException("Technician account does not exist.");
		}
		TechnicianDto technicianDto = new TechnicianDto(technician.getFirstName(), technician.getLastName(),
				technician.getEmail(), technician.getPassword());
		return technicianDto;
	}
	
	private ProfileDto convertToDto(Profile profile) {
		if (profile == null) {
			throw new IllegalArgumentException("Profile does not exist.");
		}
		ProfileDto profileDto = new ProfileDto(profile.getEmail(), profile.getFirstName(), profile.getLastName());
		return profileDto;
	}

	private TimeslotDto convertToDto(Timeslot t) {
		if (t == null) {
			throw new IllegalArgumentException("Timeslot does not exist.");
		}

		TimeslotDto timeslotDto = new TimeslotDto(t.getTime(), t.getDate(), t.getSlotID());
		return timeslotDto;
	}

	private AppointmentDto convertToDto(Appointment a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such appointment!");
		}
		Set<TimeslotDto> timeslots = new HashSet<TimeslotDto>();
		for (Timeslot timeslot : a.getTimeslot()) {
			timeslots.add(convertToDto(timeslot));
		}
		AppointmentDto appointmentDto = new AppointmentDto(a.getAppointmentID(), convertToDto(a.getCustomer()),
				convertToDto(a.getVehicle()), convertToDto(a.getTechnician()), a.getInvoice(), a.getService(),
				timeslots);
		return appointmentDto;
	}

	private List<AppointmentDto> convertToDto(List<Appointment> appointments) {
		if (appointments == null) {
			throw new IllegalArgumentException("There is no appointments.");
		}
		List<AppointmentDto> aptmtsDto = new ArrayList<AppointmentDto>();
		for (Appointment appointment : appointments) {
			aptmtsDto.add(convertToDto(appointment));
		}
		return aptmtsDto;
	}

}
