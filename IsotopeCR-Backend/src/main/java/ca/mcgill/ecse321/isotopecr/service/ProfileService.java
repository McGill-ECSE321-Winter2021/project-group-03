package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

@Service
public class ProfileService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	VehicleRepository vehicleRepository;
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	@Autowired
	AutoRepairShopRepository autoRepairShopRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	TimeslotRepository timeslotRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	/*********************************************************
	 * Technician
	 *********************************************************/

	/**
	 * Creates a technician profile with the provide arguments. All arguments are
	 * mandatory and inputs must satisfy their corresponding format, otherwise an
	 * invalidInputException is thrown. All technician profiles are registered
	 * accounts and can be used to login the application.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @return the technician created
	 * @throws InvalidInputException
	 * @author Jack Wei
	 */
	@Transactional
	public Technician createTechnicianProfile(String firstName, String lastName, String email, String password)
			throws IllegalArgumentException {

		if (profileRepository.findProfileByProfileID(String.valueOf(email.hashCode())) != null) {
			throw new IllegalArgumentException("ERROR: a profile with that email already exists.");
		}

		Technician technician = new Technician();

		if (ServiceHelperMethods.isValidEmail(email)) {
			if (ServiceHelperMethods.isValidCompanyEmail(email)) {
				technician.setEmail(email);
			} else { // valid email but not company email
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}

		if (ServiceHelperMethods.isValidName(firstName) && ServiceHelperMethods.isValidName(lastName)) {
			technician.setFirstName(firstName);
			technician.setLastName(lastName);
		} else {
			throw new IllegalArgumentException();
		}

		if (ServiceHelperMethods.isValidPassword(password)) {
			technician.setPassword(password);
			technician.setIsRegisteredAccount(true);
		} else {
			throw new IllegalArgumentException();
		}

		technician.setProfileID(String.valueOf(email.hashCode()));

		Set<DailyAvailability> dailyAvailabilities = new HashSet<DailyAvailability>();

		for (DayOfWeek day : DayOfWeek.values()) {
			DailyAvailability dailyAvailability = new DailyAvailability();
			dailyAvailability.setDay(day);
			dailyAvailability.setStartTime(Time.valueOf(LocalTime.of(9, 00)));
			dailyAvailability.setEndTime(Time.valueOf(LocalTime.of(17, 00)));
			dailyAvailability.setAvailabilityID(String.valueOf(technician.getProfileID().hashCode() * day.hashCode()));
			dailyAvailabilityRepository.save(dailyAvailability);
			dailyAvailabilities.add(dailyAvailability);
		}

		technician.setDailyAvailability(dailyAvailabilities);

		technicianRepository.save(technician);

		return technician;
	}
	
	/**
	 * Gets all the daily availabilities of the technician with the provided email. 
	 * 
	 * @param email
	 * @return all the daily availabilities of the technician found
	 * @throws IllegalArgumentException
	 * @author Zichen
	 */
	@Transactional
	public Set<DailyAvailability> getTechnicianAvailabilities(String email) throws IllegalArgumentException {
		Technician technician = getTechnician(email);
		return technician.getDailyAvailability();
	}
	
	/**
	 * Gets the technician by the email provided.
	 * 
	 * @param email
	 * @return the technician found
	 * @throws IllegalArgumentException
	 * @author Zichen
	 */
	@Transactional
	public Technician getTechnician(String email) throws IllegalArgumentException {
		if (email.isEmpty()) {
			throw new IllegalArgumentException("ERROR: the technician email is empty.");
		}
		Technician technician = technicianRepository.findTechnicianByEmail(email);
		if (technician == null) {
			throw new IllegalArgumentException("ERROR: The technician does not exist.");
		}
		return technician;
	}

	/**
	 * Updates the availability for a specific day in week.
	 * 
	 * @param tech
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @return the DailyAvailability modified
	 * @author Zichen
	 */
	@Transactional
	public DailyAvailability editTechnicianAvailability(String email, DayOfWeek day, Time startTime, Time endTime) {

		Technician technician = getTechnician(email);

		DailyAvailability foundAvailability = null;

		Set<DailyAvailability> availabilities = technician.getDailyAvailability();
		for (DailyAvailability availability : availabilities) {
			if (availability.getDay().equals(day)) {
				availability.setStartTime(startTime);
				availability.setEndTime(endTime);
				dailyAvailabilityRepository.save(availability);
				foundAvailability = availability;
			}
		}

		technician.setDailyAvailability(availabilities);
		technicianRepository.save(technician);
		return foundAvailability;
	}

	/**
	 * Add a service offered by the technician to the technician profile.
	 * 
	 * @param email
	 * @param serviceName
	 * @return the service with the provided service name
	 * @throws InvalidInputException
	 * @author Jack Wei
	 */
	@Transactional
	public ca.mcgill.ecse321.isotopecr.model.Service addServiceOfferedByTechnician(String email, String serviceName)
			throws IllegalArgumentException {

		Technician technician = technicianRepository.findTechnicianByEmail(email);
		if (technician == null) {
			throw new IllegalArgumentException("ERROR: the technician cannot be found.");
		}

		Set<ca.mcgill.ecse321.isotopecr.model.Service> services = technician.getService();
		ca.mcgill.ecse321.isotopecr.model.Service service = serviceRepository.findServiceByServiceName(serviceName);
		if (service != null) {
			services.add(service);
			technician.setService(services);
			technicianRepository.save(technician);
			return service;
		} else {
			throw new IllegalArgumentException("ERROR: Service does not exist.");
		}
	}

	/*********************************************************
	 * Customer
	 *********************************************************/

	/**
	 * Creates a customer profile with the provide arguments. First name, last name,
	 * and email are mandatory and inputs must satisfy their corresponding format,
	 * otherwise an invalidInputException is thrown. Phone number and password can
	 * be empty but otherwise they must satisfy a specific format. If password is
	 * empty, customer profile will not be a registered account.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param password
	 * @return the customer created
	 * @throws IllegalArgumentException
	 * @author Jack Wei
	 * 
	 */
	@Transactional
	public Customer createCustomerProfile(String firstName, String lastName, String email, String phoneNumber,
			String password) throws IllegalArgumentException {

		if (profileRepository.findProfileByProfileID(String.valueOf(email.hashCode())) != null) {
			throw new IllegalArgumentException("ERROR: Profile with the provided email exists.");
		}

		Customer customer = new Customer();

		Set<Vehicle> vehicles = new HashSet<Vehicle>();

		customer.setIsRegisteredAccount(false);
		customer.setVehicle(vehicles);

		if (ServiceHelperMethods.isValidEmail(email)) {
			customer.setEmail(email);
		} else {
			throw new IllegalArgumentException("ERROR: Invalid email.");
		}

		if (ServiceHelperMethods.isValidName(firstName) && ServiceHelperMethods.isValidName(lastName)) {
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
		} else {
			throw new IllegalArgumentException("ERROR: Invalid name.");
		}

		if (!phoneNumber.isEmpty()) {
			if (ServiceHelperMethods.isValidPhoneNumber(phoneNumber)) {
				customer.setPhoneNumber(phoneNumber);
			} else {
				throw new IllegalArgumentException("ERROR: Invalid phonenumber.");
			}
		} // else phone number is set to null

		if (!password.isEmpty()) {
			if (ServiceHelperMethods.isValidPassword(password)) {
				customer.setPassword(password);
				if (password != null) {
					customer.setIsRegisteredAccount(true);
				}
			} else {
				throw new IllegalArgumentException("ERROR: Invalid password.");
			}
		}

		customer.setProfileID(String.valueOf(email.hashCode()));

		customerRepository.save(customer);

		return customer;
	}
	
	/**
	 * Gets a customer by the provided email.
	 * 
	 * @param email
	 * @return the customer found.
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public Customer getCustomer(String email) throws IllegalArgumentException {
		if (email == null) {
			throw new IllegalArgumentException("ERROR: the customer email is null.");
		}
		Customer customer = customerRepository.findCustomerByEmail(email);
		if (customer == null) {
			throw new IllegalArgumentException("ERROR: the customer cannot be found.");
		}
		return customer;
	}

	/**
	 * Gets the vehicles for customers. 
	 * 
	 * @author Zichen
	 * @return the total income by all the appointments up to now
	 */
	@Transactional
	public List<Vehicle> getCustomerVehicles(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("ERROR: input customer does not exist.");
		}
		return ServiceHelperMethods.toList(customer.getVehicle());
	}

	/**
	 * Adds a vehicle with the provided info to the customer profile with the
	 * given email. All arguments must satisfy their corresponding input formats, 
	 * otherwise an invalidInputException is thrown.
	 * 
	 * @param email
	 * @param licensePlate
	 * @param year
	 * @param model
	 * @param brand
	 * @return the created vehicle
	 * @throws invalidInputException
	 * @author Jack Wei
	 * 
	 */
	@Transactional
	public Vehicle createVehicle(String email, String licensePlate, String year, String model, String brand)
			throws IllegalArgumentException {

		Customer customer = customerRepository.findCustomerByEmail(email);

		if (customer == null) {
			throw new IllegalArgumentException("ERROR: Customer does not exist.");
		}

		try {
			Vehicle vehicle = createNewVehicle(licensePlate, year, model, brand);
			Set<Vehicle> vehicles = customer.getVehicle();
			vehicles.add(vehicle);
			customer.setVehicle(vehicles);
			customerRepository.save(customer);
			return vehicle;
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	/**
	 * This helper method creates a vehicle with the provided arguments. The
	 * arguments are verified based on their corresponding format and range. An
	 * invalidInputException is thrown if input is invalid.
	 * 
	 * @param licensePlate
	 * @param year
	 * @param model
	 * @param brand
	 * @return the created vehicle
	 * @throws InvalidInputException
	 * 
	 * @author Jack Wei
	 */
	private Vehicle createNewVehicle(String licensePlate, String year, String model, String brand)
			throws IllegalArgumentException {

		Vehicle vehicle = new Vehicle();
		if (!licensePlate.isEmpty()) {
			vehicle.setLicensePlate(licensePlate);
		} else {
			throw new IllegalArgumentException("ERROR: License plate cannot be empty.");
		}
		if (ServiceHelperMethods.isValidYear(year)) {
			vehicle.setYear(year);
		} else {
			throw new IllegalArgumentException("ERROR: Year is invalid.");
		}

		if (ServiceHelperMethods.isValidModelName(model)) {
			vehicle.setModel(model);
		} else {
			throw new IllegalArgumentException("ERROR: Model name is invalid.");
		}

		if (ServiceHelperMethods.isValidBrandName(brand)) {
			vehicle.setBrand(brand);
		} else {
			throw new IllegalArgumentException("ERROR: Brand name is invalid.");
		}

		vehicleRepository.save(vehicle);

		return vehicle;
	}

	/**
	 * Deletes the vehicle with the provided licensePlate from the database and the
	 * customer profile with the given email.
	 * 
	 * @param email
	 * @param licensePlate
	 * @return the deleted vehicle
	 * @author Jack Wei
	 */
	@Transactional
	public Vehicle deleteVehicle(String email, String licensePlate) throws IllegalArgumentException {

		Customer customer = customerRepository.findCustomerByEmail(email);

		if (customer == null) {
			throw new IllegalArgumentException("ERROR: Customer does not exist.");
		}

		Boolean isDeleted = false;
		Vehicle foundVehicle = null;

		Set<Vehicle> vehicles = customer.getVehicle();

		for (Vehicle vehicle : vehicles) {
			if (vehicle.getLicensePlate().equals(licensePlate)) {
				foundVehicle = vehicle;
				isDeleted = true;
			}
		}

		vehicles.remove(foundVehicle);
		vehicleRepository.delete(foundVehicle);
		if (isDeleted) {
			customer.setVehicle(vehicles);
			customerRepository.save(customer);
		} else { // vehicle not found
			// TODO: exception? error message?
		}
		return foundVehicle;
	}

	/*********************************************************
	 * Admin
	 *********************************************************/

	/**
	 * Creates an admin profile with the provide arguments. The admin can either be
	 * the owner or administrative assistance. All arguments are mandatory and
	 * inputs must satisfy their corresponding format, otherwise an
	 * invalidInputException is thrown. All admin profile are registered accounts
	 * and can be used to log in the application.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param isOwner
	 * @param password
	 * @return the created admin
	 * @throws InvalidInputException
	 * @author Jack Wei
	 * 
	 */
	@Transactional
	public Admin createAdminProfile(String firstName, String lastName, String email, Boolean isOwner, String password)
			throws IllegalArgumentException {

		if (profileRepository.findProfileByProfileID(String.valueOf(email.hashCode())) != null) {
			throw new IllegalArgumentException("ERROR: Administrative account with that email already exists."); // TODO:
																													// exception
		}

		Admin admin = new Admin();

		if (ServiceHelperMethods.isValidEmail(email)) {
			if (ServiceHelperMethods.isValidCompanyEmail(email)) {
				admin.setEmail(email);
			} else { // valid email but not company email
				throw new IllegalArgumentException(
						"ERROR: Administrative account creation forbidden. Not a company email.");
			}
		} else {
			throw new IllegalArgumentException("ERROR: Invalid email.");
		}

		if (ServiceHelperMethods.isValidName(firstName) && ServiceHelperMethods.isValidName(lastName)) {
			admin.setFirstName(firstName);
			admin.setLastName(lastName);
		} else {
			throw new IllegalArgumentException("ERROR: Invalid name.");
		}

		if (ServiceHelperMethods.isValidPassword(password)) {
			admin.setPassword(password);
			admin.setIsRegisteredAccount(true);
		} else {
			throw new IllegalArgumentException("ERROR: Invalid password.");
		}

		admin.setIsOwner(isOwner);

		admin.setProfileID(String.valueOf(email.hashCode()));
		adminRepository.save(admin);

		return admin;
	}

	/*********************************************************
	 * All profiles
	 *********************************************************/

	/**
	 * 
	 * Changes/sets the password of the profile provided with the given password.
	 * The password must satisfy password format (one upper case letter, one lower
	 * case letter and one number; 8-20 characters long, otherwise an
	 * invalidInputException is thrown. If the profile is a customer profile,
	 * calling this method will change the profile to a registered account that can
	 * be used to log in the application.
	 * 
	 * @param email
	 * @param password
	 * @return the edited profile
	 * @throws InvalidInputException
	 * @author Jack Wei
	 * 
	 */
	@Transactional
	public Profile editPassword(String email, String password) throws IllegalArgumentException {

		Profile profile = getProfile(email);
		if (ServiceHelperMethods.isValidPassword(password)) {
			profile.setPassword(password);
			if (profile instanceof Customer) {
				profile.setIsRegisteredAccount(true);
			}
		} else {
			throw new IllegalArgumentException("ERROR: Invalid password.");
		}

		profileRepository.save(profile);
		return profile;
	}

	/**
	 * Edits/sets the phone number of the profile provided and the given phone
	 * number. The phone number must satisfy standard phone number format which can
	 * include white space, hyphen, dot and international prefix, otherwise an
	 * invalidInputException is thrown.
	 * 
	 * @param email
	 * @param phoneNumber
	 * @return the edited customer profile
	 * @throws InvalidInputException
	 * @author Jack Wei
	 * 
	 */
	@Transactional
	public Customer editPhoneNumber(String email, String phoneNumber) throws IllegalArgumentException {

		Customer customerProfile = customerRepository.findCustomerByEmail(email);

		if (customerProfile == null) {
			throw new IllegalArgumentException("ERROR: Customer does not exist.");
		}

		if (ServiceHelperMethods.isValidPhoneNumber(phoneNumber)) {
			customerProfile.setPhoneNumber(phoneNumber);
			customerRepository.save(customerProfile);
			return customerProfile;
		} else {
			throw new IllegalArgumentException("ERROR: Invalid phonenumber.");
		}
	}

	/**
	 * Deletes the user profile with the provided profile ID.
	 * 
	 * @param email
	 * @return the deleted profile
	 * @author Jack Wei
	 * 
	 */
	@Transactional
	public Profile deleteProfile(String email) {
		Profile profile = profileRepository.findProfileByProfileID(String.valueOf(email.hashCode()));

		if (profile != null) {
			profileRepository.delete(profile);
			return profile;
		} else {
			throw new IllegalArgumentException("ERROR: Profile does not exist.");
		}
	}

	/**
	 * Gets the profile by email. Throws exception when profile not found.
	 * 
	 * @param email
	 * @return the profile found
	 * @throws invalidInputException
	 * @author Jack Wei
	 */
	@Transactional
	public Profile getProfile(String email) throws IllegalArgumentException {

		Profile profile = profileRepository.findProfileByProfileID(String.valueOf(email.hashCode()));
		if (profile == null) {
			throw new IllegalArgumentException("ERROR: Profile does not exist.");
		}

		return profile;
	}

}
