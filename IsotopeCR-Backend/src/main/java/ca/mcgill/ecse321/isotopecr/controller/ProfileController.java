package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.dto.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.service.ProfileService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
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

	/*********************************************************
	 * Technician
	 *********************************************************/

	@PostMapping(value = { "technician/create", "technician/create/" })
	public TechnicianDto createTechnicianProfile(@RequestParam String email,
			@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String password) throws Exception {
		try {
			Technician technician = profileService.createTechnicianProfile(firstName, lastName, email, password);
			return ControllerHelperMethods.convertToDto(technician);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Zichen
	 * @param email
	 * @return List of DailyAvailabilityDtos
	 * @throws Exception
	 */
	@GetMapping(value = { "technician/availability/get-all/{email}", "technician/availability/get-all/{email}/" })
	public List<DailyAvailabilityDto> getTechnicianAvailabilities(@PathVariable("email") String email)
			throws Exception {
		try {
			Technician technician = profileService.getTechnician(email);
			return technician.getDailyAvailability().stream().map(r -> ControllerHelperMethods.convertToDto(r))
					.collect(Collectors.toList());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Update an availability by a technician.
	 * 
	 * @author Zichen
	 * @param email
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = { "/availability/edit/{email}", "/availability/edit/{email}/" })
	public DailyAvailabilityDto editTechnicianAvailability(@PathVariable("email") String email,
			@RequestParam String weekday,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") LocalTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") LocalTime end)
			throws Exception {
		if (!isWeekDayValid(weekday)) {
			throw new IllegalArgumentException("ERROR: input weekday is not a legal working day.");
		} else if (!isStartEndTimeValid(start, end)) {
			throw new IllegalArgumentException("ERROR: input start_time is not ahead of end_time.");
		}

		try {
			Technician technician = profileService.getTechnician(email);
			DailyAvailability availability = profileService.editTechnicianAvailability(technician,
					DailyAvailability.DayOfWeek.valueOf(weekday), Time.valueOf(start), Time.valueOf(end));
			return ControllerHelperMethods.convertToDto(availability);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param email
	 * @return List of ServiceDtos
	 * @throws Exception
	 */
	@GetMapping(value = { "/technician/service/get-all/{email}", "/technician/service/get-all/{email}/" })
	public List<ServiceDto> getTechnicianServices(@PathVariable("email") String email) throws Exception {
		try {
			Technician technician = profileService.getTechnician(email);
			return technician.getService().stream().map(r -> ControllerHelperMethods.convertToDto(r))
					.collect(Collectors.toList());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "technician/service/add/{email}", "customer/service/add/{email}/" })
	public ServiceDto addServiceOfferedByTechnician(@PathVariable("email") String email,
			@RequestParam("serviceName") String serviceName) throws Exception {
		try {
			
			Service service = (Service) profileService.addServiceOfferedByTechnician(email, serviceName);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private boolean isStartEndTimeValid(LocalTime start, LocalTime end) {
		return start.isBefore(end);
	}

	private boolean isWeekDayValid(String weekday) {
		switch (weekday) {
		case "Monday":
			return true;
		case "Tuesday":
			return true;
		case "Wednesday":
			return true;
		case "Thursday":
			return true;
		case "Friday":
			return true;
		default:
			return false;
		}
	}

	/*********************************************************
	 * Customer
	 *********************************************************/

	@PostMapping(value = { "/customer/create", "/customer/create/" })
	public CustomerDto createCustomerProfile(@RequestParam String email,
			@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String phoneNumber, @RequestParam String password)
			throws IllegalArgumentException {
		try {
			Customer customer = profileService.createCustomerProfile(firstName, lastName, email, phoneNumber, password);
			return ControllerHelperMethods.convertToDto(customer);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Zichen
	 * @param email
	 * @return List of VehicleDto
	 * @throws Exception
	 */
	@GetMapping(value = { "/customer/vehicle/get-all/{email}", "/customer/vehicle/get-all/{email}/" })
	public List<VehicleDto> getVehiclesByCustomer(@PathVariable("email") String email) throws Exception {
		try {
			Customer customer = profileService.getCustomer(email);
			return customer.getVehicle().stream().map(r -> ControllerHelperMethods.convertToDto(r))
					.collect(Collectors.toList());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "/customer/vehicle/create/{email}", "/customer/vehicle/create/{email}/" })
	public VehicleDto createVehicle(@PathVariable("email") String email,
			@RequestParam("licensePlate") String licensePlate, @RequestParam("year") String year,
			@RequestParam("model") String model, @RequestParam("brand") String brand) throws Exception {
		try {
			Customer customer = profileService.getCustomer(email);
			Vehicle vehicle = profileService.createVehicle(customer, licensePlate, year, model, brand);
			return ControllerHelperMethods.convertToDto(vehicle);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "/customer/vehicle/delete", "/customer/vehicle/delete/" })
	public VehicleDto deleteVehicle(@RequestParam String email,
			@RequestParam String licensePlate) throws Exception {
		try {
			Customer customer = profileService.getCustomer(email);
			Vehicle vehicle = profileService.deleteVehicle(customer, licensePlate);
				return ControllerHelperMethods.convertToDto(vehicle);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*********************************************************
	 * Admin
	 *********************************************************/

	@PostMapping(value = { "/admin/create", "/admin/create/" })
	public AdminDto createAdminProfile(@RequestParam String email, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String password,
			@RequestParam boolean isOwner) throws IllegalArgumentException {
		try {
			Admin admin = profileService.createAdminProfile(firstName, lastName, email, isOwner, password);
			return ControllerHelperMethods.convertToDto(admin);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*********************************************************
	 * All profiles
	 *********************************************************/
	
	@PostMapping(value = { "profiles/delete/{email}", "/profiles/delete/{email}/" })
	public ProfileDto deleteProfile(@PathVariable("email") String email)
			throws IllegalArgumentException{
		try {
			Profile profile = profileService.getProfile(email);
			profileService.deleteProfile(profile);
			return ControllerHelperMethods.convertToDto(profile);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "profiles/edit-password/{email}", "/profiles/edit-password/{email}/" })
	public ProfileDto editPassword(@RequestParam("email") String email, @RequestParam("password") String password)
			throws Exception {
		try {
			Profile profile = profileService.getProfile(email);
			profile = profileService.editPassword(profile, password);
			return ControllerHelperMethods.convertToDto(profile);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "profiles/edit-phonenumber", "/profiles/edit-phonenumber/"  })
	public CustomerDto editPhoneNumber(@RequestParam String email,
			@RequestParam String phoneNumber) throws Exception {
		try {
			Profile profile = profileService.getProfile(email);
			if (profile instanceof Customer) {
				Customer customer = profileService.editPhoneNumber(profile, phoneNumber);
				return ControllerHelperMethods.convertToDto(customer);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "/login", "/login/" })
	public Profile login(@RequestParam String email, @RequestParam String password,
			HttpSession session) throws IllegalArgumentException {

		try {
			Profile profile = profileService.getProfile(email);
			if (profile.getIsRegisteredAccount()) {
				if (password != null && profile.getPassword().equals(password)) {
					session.setAttribute(email, profile);
				} else {
					throw new IllegalArgumentException(); // TODO: Needs specific exception
				}
			} else {
				throw new IllegalArgumentException(); // TODO: Needs specific exception
			}
			return profile;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "/logout", "/logout/" })
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "logout sucessfully";
	}
}
