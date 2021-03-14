package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.dto.*;
import ca.mcgill.ecse321.isotopecr.model.*;
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
	
	
	/* ----------------------- Zichen --------------------------*/
	@GetMapping(value = { "/resources", "/resources/" })
	public List<ResourceDto> getAllResources() {
		return service.viewAllResources().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/events/{type}/{max}", "/events/{type}/{max}" })
	public ResourceDto createResource(@PathVariable("type") String type, @PathVariable("max") Integer max)
	throws IllegalArgumentException {
		// check the input validity first:
		if (resourceRepository.findResourceByResourceType(type) != null) {
			throw new IllegalArgumentException("ERROR: the resource type has existed inside the system.");
		} else if (max < 1) {
			throw new IllegalArgumentException("ERROR: the resource should at least have one availability.");
		}
		Resource resource = service.addResource(type, max);
		return convertToDto(resource);
	}

	@GetMapping(value = { "/invoice", "/invoice/" })
	public List<InvoiceDto> getAllInvoices() {
		return service.viewAllInvoices().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/incomesummary", "/incomesummary/" })
	public double getIncomeSummary() {
		return service.viewIncomeSummary();
	}
	
	@GetMapping(value = { "/availablities/{ID}", "/availabilities/{name}/" })
	public List<DailyAvailabilityDto> getTechAvailabilities(@PathVariable("name") String profileID) throws IllegalArgumentException {
		Technician tech = technicianRepository.findTechnicianByProfileID(profileID);
		tech.getDailyAvailability();
//		service.viewAvailability(name)	
//		Person person = service.createPerson(name);
//		return convertToDto(person);
	}
	
	/* ----------------------- Jack Wei --------------------------*/
	@PostMapping(value = { "/create-customer-profile", "/create-customer-profile/"})
	public CustomerDto createCustomerProfile(@RequestParam("email") String email, 
											 @RequestParam("firstName") String firstName,
											 @RequestParam("lastName") String lastName,
											 @RequestParam("phoneNumber") String phoneNumber,
											 @RequestParam("password") String password)
	throws IllegalArgumentException {
		try {
			Customer customer = service.createCustomerProfile(firstName, lastName, email, phoneNumber, password);
			return convertToDto(customer);
		} catch (invalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/create-admin-profile", "/create-admin-profile/"})
	public AdminDto createAdminProfile(@RequestParam("email") String email, 
											 @RequestParam("firstName") String firstName,
											 @RequestParam("lastName") String lastName,
											 @RequestParam("password") String password,
											 @RequestParam("isOwner") boolean isOwner)
	throws IllegalArgumentException {
		try {
			Admin admin = service.createAdminProfile(firstName, lastName, email, isOwner, password);
			return convertToDto(admin);
		} catch (invalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/create-technician-profile", "/create-technician-profile/"})
	public TechnicianDto createTechnicianProfile(@RequestParam("email") String email, 
											 @RequestParam("firstName") String firstName,
											 @RequestParam("lastName") String lastName,
											 @RequestParam("password") String password)
	throws IllegalArgumentException {
		try {
			Technician technician = service.createTechnicianProfile(firstName, lastName, email, password);
			return convertToDto(technician);
		} catch (invalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/delete-profile/{email}", "/delete-profile/{email}/"})
	public CustomerDto deleteProfile(@PathVariable("email") String email) throws IllegalArgumentException {
		try {
			Profile profile = service.getProfile(email);
			service.deleteProfile(profile);
		} catch (invalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = { "/add-vehicle/{email}", "/add-vehicle/{email}/"})
	public VehicleDto addVehicle(@PathVariable("email") String email,
								 @RequestParam("licensePlate") String licensePlate,
								 @RequestParam("year") String year,
								 @RequestParam("model") String model,
								 @RequestParam("brand") String brand) throws IllegalArgumentException {
		try {
		} catch (invalidInputException e) {
			
		}
	}
	
	@PostMapping(value = { "/login", "/login/"})
	public Profile login(@RequestParam("email") String email, 
							@RequestParam("password") String password,
							HttpSession session)
	throws IllegalArgumentException {
		try {
			Profile profile = service.getProfile(email);
			if (profile.getIsRegisteredAccount()) {
				if (password != null && profile.getPassword().equals(password)) {
					session.setAttribute(email, profile);
				} else {
					throw new invalidInputException(); // TODO: Needs specific exception
				}
			} else {
				throw new invalidInputException(); // TODO: Needs specific exception
			}
			return profile;
		} catch (invalidInputException e) {
			throw e;
		}
	}
	
	@PostMapping(value = {"/logout", "/logout/"})
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "logout sucessfully";
	}
	
	
	
	
	/* ============================== Helpers ===============================*/
	private ResourceDto convertToDto(Resource r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Resource!");
		}
		ResourceDto resourceDto = new ResourceDto(r.getResourceType(), r.getMaxAvailable());
		return resourceDto;
	}
	
	private InvoiceDto convertToDto(Invoice i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Invoice!");
		}
		// TODO: complete this one;
		//		InvoiceDto inovoiceDto = new InvoiceDto();
		//		return invoiceDto;
	}
	
	private DailyAvailabilityDto convertToDto(DailyAvailability d) {
		if (d == null) {
			throw new IllegalArgumentException("There is no such Invoice!");
		}
		DailyAvailabilityDto dailyAvailabilityDto = new DailyAvailabilityDto(d.getAvailabilityID(), d.getStartTime(), d.getEndTime(), d.getDay());
		return dailyAvailabilityDto;
	}
	
	private CustomerDto convertToDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer profile does not exist.");
		}
		CustomerDto customerDto = new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhoneNumber(), );
		return customerDto;
	}
	
	private AdminDto convertToDto(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("Administrative account does not exist.");
		}
		AdminDto adminDto = new AdminDto(admin.getFirstName(), admin.getLastName(), admin.getEmail(), admin.getPassword(), admin.getIsOwner());
		return adminDto;
	}
	
	private TechnicianDto convertToDto(Technician technician) {
		if (technician == null) {
			throw new IllegalArgumentException("Technician account does not exist.");
		}
		TechnicianDto technicianDto = new TechnicianDto(technician.getFirstName(), technician.getLastName(), technician.getEmail(), technician.getPassword());
		return technicianDto;
	}

}
