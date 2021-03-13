package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.AdminRepository;
import ca.mcgill.ecse321.isotopecr.dao.CustomerRepository;
import ca.mcgill.ecse321.isotopecr.dao.ProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.TechnicianRepository;
import ca.mcgill.ecse321.isotopecr.dao.VehicleRepository;
import ca.mcgill.ecse321.isotopecr.model.Admin;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;
import ca.mcgill.ecse321.isotopecr.model.Profile;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;

public class IsotopeCRService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private TechnicianRepository technicianRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	
	/**
	 * Creates a customer profile with the provide arguments. 
	 * First name, last name, and email are mandatory and inputs must satisfy their corresponding format, otherwise an invalidInputException is thrown. 
	 * Phone number, password and vehicle information (license plate) can be empty (null) but otherwise they must satisfy a specific format.
	 * If password is null, customer profile will not be registered account.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param password
	 * @param licensePlate
	 * @param year
	 * @param model
	 * @param brand
	 * @return
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	@Transactional
	public Customer createCustomerProfile(String firstName, String lastName, String email, String phoneNumber, String password, String licensePlate, int year, String model, String brand) throws invalidInputException {
		Customer customer = new Customer();
		
		if (isValidEmail(email)) {
			customer.setEmail(email);
		} else {
			throw new invalidInputException();
		}
		
		if (isValidName(firstName) && isValidName(lastName)) {
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
		} else {
			throw new invalidInputException();
		}
		
		if (phoneNumber != null) {
			if (isValidPhoneNumber(phoneNumber)) {
				customer.setPhoneNumber(phoneNumber);
			} else {
				throw new invalidInputException();
			}
		} // else phone number is set to null
		
		if (password != null) {
			if (isValidPassword(password)) {
				customer.setPassword(password);
				if(password != null) {
					customer.setIsRegisteredAccount(true);
				}
			} else {
				throw new invalidInputException();
			}
		}
		
		if(licensePlate != null) {
			try {
				Vehicle vehicle = createVehicle(licensePlate, year, model, brand);
				Set<Vehicle> vehicles = new HashSet<Vehicle>();
				vehicles.add(vehicle);
				customer.setVehicle(vehicles);
			} catch (Exception e) {
				//TODO
			} throw new invalidInputException();
		}
		
		customer.setProfileID(String.valueOf(email.hashCode()));
		
		customerRepository.save(customer);
		
		return customer;
	}

	/**
	 * Creates an admin profile with the provide arguments. The admin can either be the owner or administrative assistance.
	 * All arguments are mandatory and inputs must satisfy their corresponding format, otherwise an invalidInputException is thrown.
	 * All admin profile are registered accounts and can be used to log in the application.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param isOwner
	 * @param password
	 * @return
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	@Transactional
	public Admin createAdminProfile(String firstName, String lastName, String email, Boolean isOwner, String password) throws invalidInputException {
		Admin admin = new Admin();

		if (isValidEmail(email)) {
			if(isValidCompanyEmail(email)) {
				admin.setEmail(email);
			} else { // valid email but not company email
				//TODO: exception? error message?
			}
		} else {
			throw new invalidInputException();
		}

		if (isValidName(firstName) && isValidName(lastName)) {
			admin.setFirstName(firstName);
			admin.setLastName(lastName);
		} else {
			throw new invalidInputException();
		}

		if (isValidPassword(password)) {
			admin.setPassword(password);
			admin.setIsRegisteredAccount(true);
		} else {
			throw new invalidInputException();
		}

		admin.setPassword(password);
		
		admin.setIsOwner(isOwner);
		
		adminRepository.save(admin);
		
		admin.setProfileID(String.valueOf(email.hashCode()));

		return admin;
	}
	
	/**
	 * Creates a technician profile with the provide arguments.
	 * All arguments are mandatory and inputs must satisfy their corresponding format, otherwise an invalidInputException is thrown.
	 * All technician profiles are registered accounts and can be used to login the application.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param services
	 * @return
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	@Transactional
	public Technician createTechnicianProfile(String firstName, String lastName, String email, String password, Set<ca.mcgill.ecse321.isotopecr.model.Service> services) throws invalidInputException {
		Technician technician = new Technician();

		if (isValidEmail(email)) {
			if(isValidCompanyEmail(email)) {
				technician.setEmail(email);
			} else { // valid email but not company email
				//TODO: exception? error message?
			}
		} else {
			throw new invalidInputException();
		}

		if (isValidName(firstName) && isValidName(lastName)) {
			technician.setFirstName(firstName);
			technician.setLastName(lastName);
		} else {
			throw new invalidInputException();
		}

		if (isValidPassword(password)) {
			technician.setPassword(password);
			technician.setIsRegisteredAccount(true);
		} else {
			throw new invalidInputException();
		}
		
		technician.setService(services);
		
		Set<DailyAvailability> dailyAvailabilities = new HashSet<DailyAvailability>();
		DailyAvailability dailyAvailability = new DailyAvailability();
		
		for (DayOfWeek day : DayOfWeek.values()) { 
			dailyAvailability.setDay(day);
			dailyAvailability.setStartTime(Time.valueOf(LocalTime.of(9, 00)));
			dailyAvailability.setEndTime(Time.valueOf(LocalTime.of(17, 00)));
			dailyAvailabilities.add(dailyAvailability);
		}
		
		technician.setDailyAvailability(dailyAvailabilities);
		
		technician.setProfileID(String.valueOf(email.hashCode()));
		
		technicianRepository.save(technician);

		return technician;
	}	
	
	/**
	 * Changes/sets the password of the profile provided with the given password.
	 * The password must satisfy password format (one upper case letter, one lower case letter and one number; 8-20 characters long,
	 * otherwise an invalidInputException is thrown.
	 * If the profile is a customer profile, calling this method will change the profile to a registered account that can be used to log in the application.
	 * 
	 * @param currentUser
	 * @param password
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	public void editPassword(Profile currentUser, String password) throws invalidInputException {
				
		if(isValidPassword(password)) {
			currentUser.setPassword(password);
			if(currentUser instanceof Customer) {
				currentUser.setIsRegisteredAccount(true);
			}
		} else {
			throw new invalidInputException();
		}	
		
		profileRepository.save(currentUser);
	}
	
	/**
	 * Edits/sets the phone number of the profile provided and the given phone number.
	 * The phone number must satisfy standard phone number format which can include white space, hyphen, dot and international prefix, 
	 * otherwise an invalidInputException is thrown.
	 * 
	 * @param currentUser
	 * @param phoneNumber
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	public void editPhoneNumber(Profile currentUser, String phoneNumber) throws invalidInputException {
		
		Customer customerProfile = customerRepository.findCustomerByProfileID(currentUser.getProfileID());
		
		if(isValidPhoneNumber(phoneNumber)) {
			customerProfile.setPhoneNumber(phoneNumber);
		} else {
			throw new invalidInputException();
		}	
		
		customerRepository.save(customerProfile);
	}
	
	
	/**
	 * Adds a vehicle to the customer profile with the provided arguments. 
	 * All arguments must satisfy their corresponding input formats, otherwise an invalidInputException is thrown.
	 * @param currentUser
	 * @param licensePlate
	 * @param year
	 * @param model
	 * @param brand
	 * @throws invalidInputException
	 */
	public void addVehicle(Profile currentUser, String licensePlate, int year, String model, String brand) throws invalidInputException {
		
		Customer customer = customerRepository.findCustomerByProfileID(currentUser.getProfileID());
		
		try {
			Vehicle vehicle = createVehicle(licensePlate, year, model, brand);
			Set<Vehicle> vehicles = customer.getVehicle();
			vehicles.add(vehicle);
			customer.setVehicle(vehicles);
		} catch (invalidInputException e) {
			throw e;
		} 
		
		customerRepository.save(customer);
	}
	
	/**
	 * Deletes the vehicle with the provided licensePlate from the database and the customer profile.
	 * 
	 * @param currentUser
	 * @param licensePlate
	 * 
	 * @author Jack Wei
	 */
	public void deleteVehicle(Profile currentUser, String licensePlate) {
		
		Customer customer = customerRepository.findCustomerByProfileID(currentUser.getProfileID());
		
		Boolean isDeleted = false;
		
		Set<Vehicle> vehicles = customer.getVehicle();
			for (Vehicle vehicle: vehicles) {
				if(vehicle.getLicensePlate() == licensePlate) {
					vehicles.remove(vehicle);
					vehicleRepository.delete(vehicle);
					isDeleted = true;
				}
			}
			
		if(isDeleted) {
			customer.setVehicle(vehicles);
			customerRepository.save(customer);
		} else { // vehicle not found
			// TODO: exception? error message?
		}
	}
	
	/**
	 * Add a specialized service to the technician profile.
	 *  
	 * @param technician
	 * @param service
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	public void addServiceToProfile(Technician technician, ca.mcgill.ecse321.isotopecr.model.Service service) throws invalidInputException {
		
		Set<ca.mcgill.ecse321.isotopecr.model.Service> services = technician.getService();
		
		services.add(service);
		technician.setService(services);
		
		technicianRepository.save(technician);
	}
	
	/**
	 * Deletes the user profile with user profile provided.
	 * @param user
	 * 
	 * @author Jack Wei
	 */
	public void deleteProfile(Profile user) {
		profileRepository.delete(user);
	}
	
	/**
	 * Deletes the user profile with the provided profile ID.
	 * @param profileID
	 * 
	 * @author Jack Wei
	 */
	public void deleteProfile(String profileID) {
		Profile profile = profileRepository.findProfileByProfileID(profileID);
		
		if(profile != null) {
			profileRepository.delete(profile);
		} else {
			//TODO: exception? error message?
		}
	}
	
	/**
	 * This helper method checks if the input email address satisfies a standard email address format.
	 * The format must follow RFC 5322 and there should be no no leading, trailing, or consecutive dots.
	 * The domain name must include at least one dot, and the part of the domain name after the last dot can only consist of letters.
	 * The method returns a boolean value.
	 * 
	 * @param email
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidEmail(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; 
		// format permitted by RFC 5322, no leading, trailing, or consecutive dots. 
		// domain name must include at least one dot, and the part of the domain name after the last dot can only consist of letters.
		// for example: john.doe@mail.mcgill.ca
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
	}
	
	/**
	 * This helper method checks if the email address provided (must be a valid email address), is a company email.
	 * A company email follows the format xxxxx@isotopecr.ca.
	 * The method returns a boolean value.
	 * 
	 * @param validEmail
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidCompanyEmail(String validEmail) {
		String regex = "^[\\w.+\\-]+@isotopecr\\.ca$";
		// email contains "isotopecr.ca"
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(validEmail);
        return matcher.matches();
	}
	
	/**
	 * This helper method checks if a name (can be first name or last name) is valid, i.e. contains any kind of letter from any language 
	 * and can include hyphens, dots or apostrophes.
	 * The method returns a boolean value.
	 *  
	 * @param name
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidName(String name) {
		String regex = "^[\\p{L} .'-]+$";
		// contains any kind of letter from any language
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
	}
	
	/**
	 * This helper method checks if a password is valid, i.e. 8 to 20 characters, one upper case, one lower case, one digit, contains no whitespace. 
	 * The method returns a boolean value.
	 *  
	 * @param password
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidPassword(String password) {
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
		// format: Password between 8 to 20 characters. At least one upper case, one lower case, one digit, no whitespace.
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
	}
	
	/**
	 * This helper method checks if a phoneNumber is valid, i.e. standard phone number format,
	 * can include white spaces, dots, hyphens or parenthesis between consecutive numbers.
	 * The number can also include an international prefix.
	 * The method returns a boolean value.
	 * 
	 * @param phoneNumber
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidPhoneNumber(String phoneNumber) {
		String regex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"  // Number with whitespace, dots or hyphens
			      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"  // Number with parentheses
			      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"; // Number with international prefix
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
	}
	
	/**
	 * This helper method creates a vehicle with the provided arguments. The arguments are verified based on their corresponding format and range.
	 * An invalidInputException is thrown if input is invalid.
	 * 
	 * @param licensePlate
	 * @param year
	 * @param model
	 * @param brand
	 * @return Vehicle
	 * @throws invalidInputException
	 * 
	 * @author Jack Wei
	 */
	private Vehicle createVehicle(String licensePlate, int year, String model, String brand) throws invalidInputException {
		Vehicle vehicle = new Vehicle();
		if(isValidYear(year)) {
			vehicle.setYear(year);
		} else {
			throw new invalidInputException();
		}
		
		if(isValidModelName(model)) {
			vehicle.setModel(model);
		} else {
			throw new invalidInputException();
		}
		
		if(isValidBrandName(brand)) {
			vehicle.setBrand(brand);
		} else {
			throw new invalidInputException();
		}
		
		vehicleRepository.save(vehicle);
		
		return vehicle;
	}
	
	/**
	 * This helper method checks if the brand name of a vehicle is valid, 
	 * i.e. only letters and hyphen allowed and can consist only one space between words. 
	 * This method returns a boolean value.
	 * @param brand
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidBrandName(String brand) {
		String regex = "^[a-zA-Z-\s]*[a-zA-Z-]+$";
		//only letters and - allowed, only one space between words allowed
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(brand);
        return matcher.matches();
	}

	/**
	 * This helper method checks if the model name of vehicle is valid,i.e containing 1 to 30 characters.
	 * This method returns a boolean value.
	 * 
	 * @param model
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidModelName(String model) {
		String regex = "^.{1,30}$"; // 1 to 30 characters
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(model);
        return matcher.matches();
	}

	/**
	 * This helper method checks if the year of a vehicle is valid. The year must be between 1900 to 3000.
	 * This method returns a boolean value.
	 * 
	 * @param year
	 * 
	 * @author Jack Wei
	 */
	private boolean isValidYear(int year) {
		boolean isValid = false;
		if(1900 <= year || year <= 3000) { // between 1900 and 3000 for any car
			isValid = true;
		}
		return isValid;
	}
}
