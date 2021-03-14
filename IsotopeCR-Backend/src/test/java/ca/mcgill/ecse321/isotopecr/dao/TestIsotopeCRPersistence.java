package ca.mcgill.ecse321.isotopecr.dao;

import java.sql.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestIsotopeCRPersistence {
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

	@AfterEach
	public void clearDatabase() {
		appointmentRepository.deleteAll();
		autoRepairShopRepository.deleteAll();
		profileRepository.deleteAll();
		companyProfileRepository.deleteAll();
		serviceRepository.deleteAll();
		resourceRepository.deleteAll();
		technicianRepository.deleteAll();
		timeslotRepository.deleteAll();
		customerRepository.deleteAll();
		adminRepository.deleteAll();
		vehicleRepository.deleteAll();
		dailyAvailabilityRepository.deleteAll();
		invoiceRepository.deleteAll();
	}

	/**
	 * Test for Persistence of AutoRepairShop
	 * 
	 * @author Jack Wei
	 */
	@Test
	public void testPersistAndLoadAutoRepairShop() {
		// Setting up the objects and variables

		// Create an AutoRepairShop
		String AutoRepairShopID = "ARS";
		AutoRepairShop ars = new AutoRepairShop();
		ars.setAutoRepairShopID(AutoRepairShopID);
		autoRepairShopRepository.save(ars);

		// Test findAutoRepairShopByAutoRepairShopID
		ars = null;

		ars = autoRepairShopRepository.findAutoRepairShopByAutoRepairShopID(AutoRepairShopID);
		assertNotNull(ars);
		assertEquals(ars.getAutoRepairShopID(), AutoRepairShopID);
	}

	/**
	 * Test for Persistence of CompanyProfile
	 * 
	 * @author Jack Wei
	 */
	@Test
	public void testPersistAndLoadCompanyProfile() {
		// Setting up the objects and variables

		// Create a CompanyProfile
		String companyName = "CompanyName";
		String address = "CompanyAddress";
		String workingHours = "workingHours";
		CompanyProfile cp = createCompanyProfile(companyName, address, workingHours);

		// Test findCopmanyProfileByAddress
		cp = null;

		cp = companyProfileRepository.findCompanyProfileByAddress(address);
		assertNotNull(cp);
		assertEquals(address, cp.getAddress());
	}

	/**
	 * Test for Persistence of Resource
	 * 
	 * @author Mathieu-Joseph Magri
	 */
	@Test
	public void testPersistAndLoadResource() {
		// Setting up the objects and variables

		// Create a resource
		String resourceType = "resourceType";
		Integer maxAvailability = 4;
		Resource r = createResource(resourceType, maxAvailability);

		// Test findResourceByResourceType
		r = null;

		r = resourceRepository.findResourceByResourceType(resourceType);
		assertNotNull(r);
		assertEquals(resourceType, r.getResourceType());
	}

	/**
	 * Test for Persistence of Timeslot
	 * 
	 * @author Mathieu-Joseph Magri
	 */
	@Test
	public void testPersistAndLoadTimeslot() {
		// Setting up the objects and variables
		java.sql.Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		java.sql.Time time = java.sql.Time.valueOf(LocalTime.of(11, 35));
		String slotID = "slotID";

		Timeslot ts = createTimeslot(date, time, slotID);

		// Test findTimeslotBySlotID
		ts = null;

		ts = timeslotRepository.findTimeslotBySlotID(slotID);
		assertNotNull(ts);
		assertEquals(slotID, ts.getSlotID());
	}

	/**
	 * Test for Persistence of Admin
	 * 
	 * @author Zichen Chang
	 */
	@Test
	public void testPersistAndLoadAdmin() {
		// Setting up the objects and variables

		// Create an Admin
		String firstName = "John";
		String lastName = "Doe";
		String email = "john.doe@mail.mcgill.ca";
		Boolean isRegistered = true;
		String profileID = "C1";
		String password = "12345";
		Boolean isOwner = true;
		Admin a = createAdmin(isOwner, firstName, lastName, email, isRegistered, profileID, password);

		// Test findAdminByProfileID
		a = null;

		a = adminRepository.findAdminByProfileID(profileID);
		assertNotNull(a);
		assertEquals(profileID, a.getProfileID());
	}

	/**
	 * Test for Persistence of Customer
	 * 
	 * @author Zichen Chang
	 */
	@Test
	public void testPersistAndLoadCustomer() {
		// Setting up the objects and variables

		// Create two vehicles
		String brand1 = "Nissan";
		String brand2 = "Volkswagen";
		String model1 = "GT-R";
		String model2 = "Tiguan";
		int year1 = 2016;
		int year2 = 2018;
		String licensePlate1 = "xxxx1";
		String licensePlate2 = "xxxx2";
		Vehicle v1 = createVehicle(licensePlate1, year1, model1, brand1);
		Vehicle v2 = createVehicle(licensePlate2, year2, model2, brand2);

		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(v1);
		vehicles.add(v2);

		// Create a customer
		String firstName = "John";
		String lastName = "Doe";
		String email = "john.doe@mail.mcgill.ca";
		String phoneNumber = "613-555-xxxx";
		String password = "12345";
		Boolean isRegistered = true;
		String profileID = "C1";
		Customer c = createCustomer(vehicles, phoneNumber, firstName, lastName, email, isRegistered, profileID,
				password);

		// Test findCustomerByProfileID
		c = null;

		c = customerRepository.findCustomerByProfileID(profileID);
		assertNotNull(c);
		assertEquals(profileID, c.getProfileID());

		// Test findCustomerByVehicle
		c = null;

		c = customerRepository.findCustomerByVehicle(v1);
		assertNotNull(c);
		assertEquals(profileID, c.getProfileID());

		c = null;

		c = customerRepository.findCustomerByVehicle(v2);
		assertNotNull(c);
		assertEquals(profileID, c.getProfileID());
	}

	/**
	 * Test for Persistence of Service
	 * 
	 * @author Jiatong Niu
	 */
	@Test
	public void testPersistAndLoadService() {
		// Setting up the objects and variables

		// Create a Resource
		String type = "Resource Type";
		int maxAvailable = 4;
		Resource resource = createResource(type, maxAvailable);

		// Create a Service
		String serviceName = "Service Name";
		int duration = 3;
		double price = 20.00;
		Service s = createService(resource, serviceName, duration, price);

		// Test findServiceByServiceName
		s = null;

		s = serviceRepository.findServiceByServiceName(serviceName);
		assertNotNull(s);
		// Check that the serviceName matches
		assertEquals(serviceName, s.getServiceName());
		// Check that the service's resource matches
		assertEquals(resource.getResourceType(), s.getResource().getResourceType());

		// Test findServiceByResource
		s = null;

		List<Service> servicesFound = serviceRepository.findServiceByResource(resource);
		assertNotNull(servicesFound);
		assertEquals(servicesFound.size(), 1); // There is only one service in this test
		for (int i = 0; i < servicesFound.size(); i++) {
			// check that the resourceType matches
			assertEquals(servicesFound.get(i).getResource().getResourceType(), resource.getResourceType());
		}
	}

	/**
	 * Test for Persistence of DailyAvailability
	 * 
	 * @author Jiatong Niu
	 */
	@Test
	public void testPersistAndLoadDailyAvailability() {
		// Setting up the objects and variables

		// Create DailyAvailability
		DayOfWeek day = DayOfWeek.Tuesday;
		java.sql.Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		java.sql.Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		String availabilityID = "availabilityID";
		DailyAvailability dA = createDailyAvailability(availabilityID, day, startTime, endTime);

		// Test findDailyAvailabilityByAvailabilityID
		dA = null;

		dA = dailyAvailabilityRepository.findDailyAvailabilityByAvailabilityID(availabilityID);
		assertNotNull(dA);
		assertEquals(availabilityID, dA.getAvailabilityID());
	}

	/**
	 * Test for Persistence of Invoice
	 * 
	 * @author Jiatong Niu
	 */
	@Test
	public void testPersistAndLoadInvoice() {
		// Setting up the objects and variables

		// Create an Invoice
		String invoiceID = "12345";
		double cost = 150;
		boolean isPaid = true;
		Invoice invoice = createInvoice(invoiceID, cost, isPaid);

		// Test findInvoiceByInvoiceID
		invoice = null;

		invoice = invoiceRepository.findInvoiceByInvoiceID(invoiceID);
		assertNotNull(invoice);
		assertEquals(invoiceID, invoice.getInvoiceID());
	}

	/**
	 * Test for Persistence of Technician
	 * 
	 * @author Victoria Iannotti
	 */
	@Test
	public void testPersistAndLoadTechnician() {
		// Setting up the objects and variables

		// Create a DailyAvailability
		DayOfWeek day = DayOfWeek.Tuesday;
		java.sql.Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		java.sql.Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		String availabilityID = "availabilityID";
		DailyAvailability dA = createDailyAvailability(availabilityID, day, startTime, endTime);

		Set<DailyAvailability> dAs = new HashSet<DailyAvailability>();
		dAs.add(dA);

		// Create a Resource
		String type = "Resource Type";
		int maxAvailable = 4;
		Resource resource = createResource(type, maxAvailable);

		// Create a Service
		String serviceName = "Service Name";
		double price = 20.00;
		int duration = 3;
		Service service = createService(resource, serviceName, duration, price);

		Set<Service> services = new HashSet<Service>();
		services.add(service);

		String firstName = "firstName";
		String lastName = "lastName";
		String email = "email";
		String password = "password";
		boolean isRegisteredAccount = true;
		String profileID = "profileID";
		Technician t = createTechnician(services, dAs, firstName, lastName, email, isRegisteredAccount, profileID,
				password);

		// Test findTechnicianByProfileID
		t = null;

		t = technicianRepository.findTechnicianByProfileID(profileID);
		assertNotNull(t);
		assertEquals(t.getProfileID(), profileID);
	}

	/**
	 * Test for Persistence of Vehicle
	 * 
	 * @author Victoria Iannotti
	 */
	@Test
	public void testPersistAndLoadVehicle() {
		// Setting up the objects and variables
		String brand = "Nissan";
		String model = "GT-R";
		int year = 2016;
		String licensePlate = "xxxx1";
		Vehicle v = createVehicle(licensePlate, year, model, brand);

		// Test findVehicleByLicensePlate
		v = null;

		v = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		assertNotNull(v);
		assertEquals(licensePlate, v.getLicensePlate());
	}

	/**
	 * Test for Persistence of Appointment
	 * 
	 * @author Victoria Iannotti
	 */
	@Test
	public void testPersistAndLoadAppointment() {
		// Setting up the objects and variables

		// Create a Timeslot
		java.sql.Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		java.sql.Time time = java.sql.Time.valueOf(LocalTime.of(11, 35));
		String slotID = "slotID";
		Timeslot timeslot = createTimeslot(date, time, slotID);

		Set<Timeslot> timeslots = new HashSet<Timeslot>();
		timeslots.add(timeslot);

		// Create a Resource
		String resourceType = "resourceType";
		Integer maxAvailability = 4;
		Resource resource = createResource(resourceType, maxAvailability);

		// Create a Service
		String name = "Service Name";
		double price = 20.00;
		int duration = 3;
		Service service = createService(resource, name, duration, price);

		Set<Service> services = new HashSet<Service>();
		services.add(service);

		// Create an Invoice
		String invoiceID = "12345";
		double cost = 150;
		boolean isPaid = true;
		Invoice invoice = createInvoice(invoiceID, cost, isPaid);

		// Create a Vehicle
		String brand1 = "Nissan";
		String model1 = "GT-R";
		int year1 = 2016;
		String licensePlate1 = "xxxx1";
		Vehicle vehicle = createVehicle(licensePlate1, year1, model1, brand1);

		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle);

		// Create a Customer
		String firstName = "John";
		String lastName = "Doe";
		String email = "john.doe@mail.mcgill.ca";
		String phoneNumber = "613-555-xxxx";
		String password = "12345";
		Boolean isRegistered = true;
		String profileID = "C1";
		Customer c1 = createCustomer(vehicles, phoneNumber, firstName, lastName, email, isRegistered, profileID,
				password);

		// Create a DailyAvailability
		DayOfWeek day = DayOfWeek.Tuesday;
		java.sql.Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		java.sql.Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		String availabilityID = "availabilityID";
		DailyAvailability dA = createDailyAvailability(availabilityID, day, startTime, endTime);

		Set<DailyAvailability> dAs = new HashSet<DailyAvailability>();
		dAs.add(dA);

		// Create a Technician
		String firstName1 = "firstName";
		String lastName1 = "lastName";
		String email1 = "email";
		String password1 = "password";
		boolean isRegisteredAccount = true;
		String profileID1 = "profileID";
		Technician t1 = createTechnician(services, dAs, firstName1, lastName1, email1, isRegisteredAccount, profileID1,
				password1);

		// Create an Appointment
		String appointmentID = "appointmentID";
		Appointment aptmt = createAppointment(appointmentID, c1, vehicle, t1, invoice, service, timeslots);

		// Test findAppointmentByAppointmentID
		aptmt = null;

		aptmt = appointmentRepository.findAppointmentByAppointmentID(appointmentID);
		assertNotNull(aptmt);
		assertEquals(appointmentID, aptmt.getAppointmentID());

		// Test findAppointmentByCustomer
		List<Appointment> aptmts = appointmentRepository.findAppointmentByCustomer(c1);
		assertNotNull(aptmts);
		for (int i = 0; i < aptmts.size(); i++) {
			// check that the customer profileID matches
			assertEquals(aptmts.get(i).getCustomer().getProfileID(), c1.getProfileID());
		}

		// Test findAppointmentByVehicle
		aptmts = appointmentRepository.findAppointmentByVehicle(vehicle);
		assertNotNull(aptmts);
		for (int i = 0; i < aptmts.size(); i++) {
			// check that the vehicle licensePlate
			assertEquals(aptmts.get(i).getVehicle().getLicensePlate(), vehicle.getLicensePlate());
		}

		// Test findAppointmentByTechnician
		aptmts = appointmentRepository.findAppointmentByTechnician(t1);
		assertNotNull(aptmts);
		for (int i = 0; i < aptmts.size(); i++) {
			// check that the technician profileID matches
			assertEquals(aptmts.get(i).getTechnician().getProfileID(), t1.getProfileID());
		}

		// Test findAppointmentByTimeslot
		aptmts = appointmentRepository.findAppointmentByTimeslot(timeslot);
		assertNotNull(aptmts);
		for (int i = 0; i < aptmts.size(); i++) {
			// check that the timeslot is contained in that appointment
			assertTrue(aptmts.get(i).getTimeslot().contains(timeslot));
		}

		// Test findAppointmentByService
		aptmts = appointmentRepository.findAppointmentByService(service);
		assertNotNull(aptmts);
		for (int i = 0; i < aptmts.size(); i++) {
			// check that the serviceName matches
			assertEquals(aptmts.get(i).getService().getServiceName(), service.getServiceName());
		}
	}

	// ---------------------------------//
	// ---------HELPER METHODS----------//
	// ---------------------------------//

	// Helper method to create a Resource
	private Resource createResource(String type, Integer maxAvailable) {
		Resource resource = new Resource();
		resource.setResourceType(type);
		resource.setMaxAvailable(maxAvailable);
		resourceRepository.save(resource);
		return resource;
	}

	// Helper method to create a Customer
	private Customer createCustomer(Set<Vehicle> vehicles, String phoneNumber, String firstName, String lastName,
			String email, boolean isRegistered, String profileID, String password) {
		Customer customer = new Customer();
		customer.setVehicle(vehicles);
		customer.setPhoneNumber(phoneNumber);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmail(email);
		customer.setIsRegisteredAccount(isRegistered);
		customer.setProfileID(profileID);
		customer.setPassword(password);
		customerRepository.save(customer);
		return customer;
	}

	// Helper method to create a Technician
	private Technician createTechnician(Set<Service> services, Set<DailyAvailability> dailyAvailabilities,
			String firstName, String lastName, String email, boolean isRegistered, String profileID, String password) {
		Technician technician = new Technician();
		technician.setService(services);
		technician.setDailyAvailability(dailyAvailabilities);
		technician.setFirstName(firstName);
		technician.setLastName(lastName);
		technician.setEmail(email);
		technician.setIsRegisteredAccount(isRegistered);
		technician.setProfileID(profileID);
		technician.setPassword(password);
		technicianRepository.save(technician);
		return technician;
	}

	// Helper method to create a CompanyProfile
	private CompanyProfile createCompanyProfile(String companyName, String address, String workingHours) {
		CompanyProfile companyProfile = new CompanyProfile();
		companyProfile.setCompanyName(companyName);
		companyProfile.setAddress(address);
		companyProfile.setWorkingHours(workingHours);
		companyProfileRepository.save(companyProfile);
		return companyProfile;
	}

	// Helper method to create a Service
	private Service createService(Resource resource, String serviceName, Integer duration, double price) {
		Service service = new Service();
		service.setResource(resource);
		service.setServiceName(serviceName);
		service.setDuration(duration);
		service.setPrice(price);
		serviceRepository.save(service);
		return service;
	}

	// Helper method to create a Timeslot
	private Timeslot createTimeslot(java.sql.Date date, java.sql.Time time, String slotID) {
		Timeslot timeslot = new Timeslot();
		timeslot.setTime(time);
		timeslot.setSlotID(slotID);
		timeslot.setDate(date);
		timeslotRepository.save(timeslot);
		return timeslot;
	}

	// Helper method to create a Invoice
	private Invoice createInvoice(String invoiceID, double cost, boolean isPaid) {
		Invoice invoice = new Invoice();
		invoice.setInvoiceID(invoiceID);
		invoice.setCost(cost);
		invoice.setIsPaid(isPaid);
		invoiceRepository.save(invoice);
		return invoice;
	}

	// Helper method to create a Vehicle
	private Vehicle createVehicle(String licensePlate, Integer year, String model, String brand) {
		Vehicle vehicle = new Vehicle();
		vehicle.setLicensePlate(licensePlate);
		vehicle.setYear(year);
		vehicle.setModel(model);
		vehicle.setBrand(brand);
		vehicleRepository.save(vehicle);
		return vehicle;
	}

	// Helper method to create a Admin
	private Admin createAdmin(boolean isOwner, String firstName, String lastName, String email, boolean isRegistered,
			String profileID, String password) {
		Admin admin = new Admin();
		admin.setIsOwner(isOwner);
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		admin.setEmail(email);
		admin.setIsRegisteredAccount(isRegistered);
		admin.setProfileID(profileID);
		admin.setPassword(password);
		adminRepository.save(admin);
		return admin;
	}

	// Helper method to create a DailyAvailability
	private DailyAvailability createDailyAvailability(String availabilityID, DayOfWeek day, Time startTime,
			Time endTime) {
		DailyAvailability dailyAvailability = new DailyAvailability();
		dailyAvailability.setAvailabilityID(availabilityID);
		dailyAvailability.setDay(day);
		dailyAvailability.setStartTime(startTime);
		dailyAvailability.setEndTime(endTime);
		dailyAvailabilityRepository.save(dailyAvailability);
		return dailyAvailability;
	}

	// Helper method to create a Appointment
	private Appointment createAppointment(String appointmentID, Customer customer, Vehicle vehicle,
			Technician technician, Invoice invoice, Service service, Set<Timeslot> timeslots) {
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

}
