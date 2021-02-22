package ca.mcgill.ecse321.isotopecr.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

//@EntityScan("ca.mcgill.ecse321.isotopecr.model")
//@ExtendWith(SpringExtension.class)
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

	
	
	@Test 
	public void testPersistAndLoadCompanyProfile() {
		
		String name = "CompanyName";
		String address="CompanyAddress";
		String workingHours ="workingHours";		
		CompanyProfile cp = new CompanyProfile();
		cp.setAddress(address);
		cp.setCompanyName(name);
		cp.setWorkingHours(workingHours);

		companyProfileRepository.save(cp);
		
		cp=null;
		
		cp=companyProfileRepository.findCompanyProfileByAddress(address);
		assertNotNull(cp);
		assertEquals(address,cp.getAddress());
		assertEquals("1","1");
	}

	
	@Test
	public void testPersistAndLoadResource() {
		String resourceType ="resourceType";
		Integer maxAvailability =4;
		
		Resource r = new Resource();
		r.setMaxAvailable(maxAvailability);
		r.setResourceType(resourceType);
		
		resourceRepository.save(r);
		
		r=null;
		
		r=resourceRepository.findResourceByResourceType(resourceType);
		
		assertNotNull(r);
		assertEquals(resourceType,r.getResourceType());
		
		
	}
	
	
	@Test
	public void testPersistAndLoadTimeslot() {
		java.sql.Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		java.sql.Time time = java.sql.Time.valueOf(LocalTime.of(11, 35));
		String slotID ="slotID";
		
		Timeslot ts = new Timeslot();
		ts.setDate(date);
		ts.setSlotID(slotID);
		ts.setTime(time);
		
		timeslotRepository.save(ts);
		
		ts=null;
		
		ts=timeslotRepository.findTimeslotBySlotID(slotID);
		
		assertNotNull(ts);
		assertEquals(slotID,ts.getSlotID());
		
	}
	
	@Test
	public void testPersistAndLoadAdmin() {
		
		String firstName = "John";
		String lastName = "Doe";
		String email = "john.doe@mail.mcgill.ca";
		Boolean isRegistered = true;
		String profileID = "C1";
		String password = "12345";
		Boolean isOwner = true;
		
		Admin a = new Admin();
		
		a.setFirstName(firstName);
		a.setLastName(lastName);
		a.setEmail(email);
		a.setIsRegisteredAccount(isRegistered);
		a.setProfileID(profileID);
		a.setPassword(password);
		a.setIsOwner(isOwner);
		
		adminRepository.save(a);
		
		// Test find by profileID
		
		a = null;
		
		a = adminRepository.findAdminByProfileID(profileID);
		assertNotNull(a);
		assertEquals(profileID, a.getProfileID());
		
	}
	
		@Test
	public void testPersistAndLoadCustomer() {
			
			// Create two vehicles
			String brand1 = "Nissan";
			String brand2 = "Volkswagen";
			String model1 = "GT-R";
			String model2 = "Tiguan";
			int year1 = 2016;
			int year2 = 2018;
			String licensePlate1 = "xxxx1";
			String licensePlate2 = "xxxx2";
			
			
			Vehicle v1 = new Vehicle();
			Vehicle v2 = new Vehicle();
			v1.setBrand(brand1);
			v2.setBrand(brand2);
			v1.setModel(model1);
			v2.setBrand(model2);
			v1.setYear(year1);
			v2.setYear(year2);
			v1.setLicensePlate(licensePlate1);
			v2.setLicensePlate(licensePlate2);
//			v1.setCustomer(a);
//			v2.setCustomer(a);
			
			vehicleRepository.save(v1);
			vehicleRepository.save(v2);
			
			Set<Vehicle> vehicles = new HashSet<Vehicle> (); 
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
			Customer c = new Customer();
			
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setEmail(email);
			c.setPhoneNumber(phoneNumber);
			c.setIsRegisteredAccount(isRegistered);
			c.setProfileID(profileID);
			c.setPassword(password);
			c.setVehicle(vehicles);
			
			customerRepository.save(c);
			
			// Test find by profileID
			
			c = null;
			
			c = customerRepository.findCustomerByProfileID(profileID);
			assertNotNull(c);
			assertEquals(profileID, c.getProfileID());
			
			// Test find by vehicles
			
			c = null;
			c = customerRepository.findByVehicle(v1);
			assertNotNull(c);
			assertEquals(profileID, c.getProfileID());
			
			c = null;
			c = customerRepository.findByVehicle(v2);
			assertNotNull(c);
			assertEquals(profileID, c.getProfileID());
			
		}

	@Test
	public void testPersistAndLoadService() {

	
		// Create a Resource

		String type = "Resource Type";
		int maxAvailable = 4;
		
		Resource resource = new Resource();
		resource.setResourceType(type);
		resource.setMaxAvailable(maxAvailable);

		// Save the resource
		resourceRepository.save(resource);

		// Create a Service
		String name = "Service Name";
		double price = 20.00;
		int duration = 3;

		Service service = new Service();
		service.setServiceName(name);
		service.setPrice(price);
		service.setDuration(duration);
		service.setResource(resource);

		// Save the Service
		serviceRepository.save(service);


		// Test findServiceByName
		service = null;

		service = serviceRepository.findServiceByServiceName(name);
		assertNotNull(service);
		assertEquals(name, service.getServiceName());
		assertEquals(resource.getResourceType(),service.getResource().getResourceType());

//		// Test findServiceByResource
//		service = null;
//	
//		List<Service> servicesFound = serviceRepository.findByResource(resource);
//		assertNotNull(servicesFound);
//		assertEquals(services.size(), servicesFound.size());
//		for(int i = 0; i<servicesFound.size(); i++){
//			assertEquals(servicesFound.get(i).getResource().getResourceType(), resource.getResourceType());
//		}
	}
	@Test
	public void testPersistAndLoadDailyAvailability() {
		DayOfWeek day= DayOfWeek.Tuesday;
		java.sql.Time startTime= java.sql.Time.valueOf(LocalTime.of(11, 35));
		java.sql.Time endTime =  java.sql.Time.valueOf(LocalTime.of(13, 25));
		String availabilityID ="availabilityID";
		
		DailyAvailability dA = new DailyAvailability();
		dA.setAvailabilityID(availabilityID);
		dA.setDay(day);
		dA.setStartTime(startTime);
		dA.setEndTime(endTime);
		
		dailyAvailabilityRepository.save(dA);
		
		dA=null;
		
		dA=dailyAvailabilityRepository.findDailyAvailabilityByAvailabilityID(availabilityID);
		assertNotNull(dA);
		assertEquals(availabilityID, dA.getAvailabilityID());
	}
	
	@Test
	public void testPersistAndLoadInvoice() {
		
	Invoice invoice = new Invoice();

	  
	  String invoiceID = "12345";
	  double cost = 150;
	  boolean isPaid = true;
	  
	  
	  invoice.setInvoiceID(invoiceID);
	  invoice.setCost(cost);
	  invoice.setIsPaid(isPaid);
	  invoiceRepository.save(invoice);
	  
	  invoice = null;
	  
	  
	  invoice = invoiceRepository.findInvoiceByInvoiceID(invoiceID);
	  assertNotNull(invoice);
	  assertEquals(invoiceID, invoice.getInvoiceID());
	  
	}
	
	@Test
	public void testPersistAndLoadTechnician() {
		
		DayOfWeek day= DayOfWeek.Tuesday;
		java.sql.Time startTime= java.sql.Time.valueOf(LocalTime.of(11, 35));
		java.sql.Time endTime =  java.sql.Time.valueOf(LocalTime.of(13, 25));
		String availabilityID ="availabilityID";
		
		
		DailyAvailability dA = new DailyAvailability();
		dA.setAvailabilityID(availabilityID);
		dA.setDay(day);
		dA.setStartTime(startTime);
		dA.setEndTime(endTime);

	
		
		Set<DailyAvailability> dAs = new HashSet<DailyAvailability>();
		dAs.add(dA);
			
		dailyAvailabilityRepository.save(dA);
		
		String type = "Resource Type";
		int maxAvailable = 4;
		
		Resource resource = new Resource();
		resource.setResourceType(type);
		resource.setMaxAvailable(maxAvailable);
		resourceRepository.save(resource);

		// Create a Service
		String name = "Service Name";
		double price = 20.00;
		int duration = 3;

		Service service = new Service();
		service.setServiceName(name);
		service.setPrice(price);
		service.setDuration(duration);
		service.setResource(resource);

		// Save the Service
		
		
		Set<Service> services = new HashSet<Service>();
		services.add(service);
		
		serviceRepository.save(service);
		
		
		String firstName ="firstName";
		String lastName ="lastName";
		String email ="email";
		String password ="password";
		boolean isRegisteredAccount = true;
		String profileID = "profileID";
		
		Technician t = new Technician();
		t.setProfileID(profileID);
		t.setEmail(email);
		t.setFirstName(firstName);
		t.setLastName(lastName);
		t.setIsRegisteredAccount(isRegisteredAccount);
		t.setPassword(password);
		t.setService(services);
		t.setDailyAvailability(dAs);
		technicianRepository.save(t);
		
		t = null;
		
		t=technicianRepository.findTechnicianByProfileID(profileID);
		assertNotNull(t);
		assertEquals(t.getProfileID(),profileID);
			
	}
	
	@Test
	public void testPersistAndLoadVehicle() {
		// Create two vehicles
		String brand1 = "Nissan";
		String model1 = "GT-R";
		int year1 = 2016;

		String licensePlate1 = "xxxx1";
	
		Vehicle v1 = new Vehicle();
		v1.setBrand(brand1);
		v1.setModel(model1);
		v1.setYear(year1);
		v1.setLicensePlate(licensePlate1);
		vehicleRepository.save(v1);
		v1=null;
		v1=vehicleRepository.findVehicleByLicensePlate(licensePlate1);
		assertNotNull(v1);
		assertEquals(licensePlate1,v1.getLicensePlate());

		
	}
	

	@Test
	public void testPersistAndLoadAppointment() {
		java.sql.Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		java.sql.Time time = java.sql.Time.valueOf(LocalTime.of(11, 35));
		String slotID ="slotID";
		Timeslot timeslot = createTimeslot(date,time,slotID);
		Set<Timeslot> timeslots = new HashSet<Timeslot>();
		timeslots.add(timeslot);
		
		String resourceType ="resourceType";
		Integer maxAvailability =4;
		Resource resource = createResource(resourceType,maxAvailability);
		
		String name = "Service Name";
		double price = 20.00;
		int duration = 3;
		Service service = createService(resource,name,duration,price);
		Set<Service> services = new HashSet<Service>();
		services.add(service);
		
		String invoiceID = "12345";
		double cost = 150;
		boolean isPaid = true;
		Invoice invoice = createInvoice(invoiceID,cost,isPaid);
		
		String brand1 = "Nissan";
		String model1 = "GT-R";
		int year1 = 2016;
		String licensePlate1 = "xxxx1";
		Vehicle vehicle = createVehicle(licensePlate1,year1,model1,brand1);
		
		String firstName = "John";
		String lastName = "Doe";
		String email = "john.doe@mail.mcgill.ca";
		String phoneNumber = "613-555-xxxx";
		String password = "12345";
		Boolean isRegistered = true;
		String profileID = "C1";
		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle);
		Customer c1 = createCustomer(vehicles,phoneNumber,firstName,lastName,email,isRegistered,profileID,password);
		
		DayOfWeek day= DayOfWeek.Tuesday;
		java.sql.Time startTime= java.sql.Time.valueOf(LocalTime.of(11, 35));
		java.sql.Time endTime =  java.sql.Time.valueOf(LocalTime.of(13, 25));
		String availabilityID ="availabilityID";
		
		DailyAvailability dA = createDailyAvailability(availabilityID,day,startTime,endTime);
		Set<DailyAvailability> dAs = new HashSet<DailyAvailability>();
		dAs.add(dA);
		
		String firstName1 ="firstName";
		String lastName1 ="lastName";
		String email1 ="email";
		String password1 ="password";
		boolean isRegisteredAccount = true;
		String profileID1 = "profileID";
		Technician t1 = createTechnician(services,dAs,firstName1,lastName1,email1,isRegisteredAccount,profileID1,password1);
		
		String appointmentID = "appointmentID";
		Appointment aptmt = createAppointment(appointmentID,c1,vehicle,t1,invoice,service,timeslots);
		
		aptmt=null;
		
		aptmt=appointmentRepository.findAppointmentByAppointmentID(appointmentID);
		assertNotNull(aptmt);
		assertEquals(appointmentID,aptmt.getAppointmentID());
		
	}
    private Resource createResource(String type, Integer maxAvailable) {
		Resource resource = new Resource();
		resource.setResourceType(type);
		resource.setMaxAvailable(maxAvailable);
		resourceRepository.save(resource);
		return resource;
	}
	
	private Customer createCustomer(Set<Vehicle> vehicles, String phoneNumber, String firstName, String lastName, String email, boolean isRegistered, String profileID, String password) {
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
	
	private Technician createTechnician(Set<Service> services, Set<DailyAvailability> dailyAvailabilities, String firstName, String lastName, String email, boolean isRegistered, String profileID, String password) {
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
	
	private CompanyProfile createCompanyProfile(String companyName, String address, String workingHours) {
		CompanyProfile companyProfile = new CompanyProfile();
		companyProfile.setCompanyName(companyName);
		companyProfile.setAddress(address);
		companyProfile.setWorkingHours(workingHours);
		companyProfileRepository.save(companyProfile);
		return companyProfile;
	}
	
	private Service createService(Resource resource, String serviceName, Integer duration, double price) {
		Service service = new Service();
		service.setResource(resource);
		service.setServiceName(serviceName);
		service.setDuration(duration);
		service.setPrice(price);
		serviceRepository.save(service);
		return service;
	}
	
	private Timeslot createTimeslot(java.sql.Date date, java.sql.Time time, String slotID) {
		Timeslot timeslot = new Timeslot();
		timeslot.setTime(time);
		timeslot.setSlotID(slotID);
		timeslot.setDate(date);
		timeslotRepository.save(timeslot);
		return timeslot;
	}
	
	private Invoice createInvoice(String invoiceID, double cost, boolean isPaid) {
		Invoice invoice = new Invoice();
		invoice.setInvoiceID(invoiceID);
		invoice.setCost(cost);
		invoice.setIsPaid(isPaid);
		invoiceRepository.save(invoice);
		return invoice;
	}
	
	private Vehicle createVehicle(String licensePlate, Integer year, String model, String brand) {
		Vehicle vehicle = new Vehicle();
		vehicle.setLicensePlate(licensePlate);
		vehicle.setYear(year);
		vehicle.setModel(model);
		vehicle.setBrand(brand);
		vehicleRepository.save(vehicle);
		return vehicle;
	}
	
	private Admin createAdmin(boolean isOwner) {
		Admin admin = new Admin();
		admin.setIsOwner(isOwner);
		adminRepository.save(admin);
		return admin;
	}
	
	private DailyAvailability createDailyAvailability(String availabilityID, DayOfWeek day, Time startTime, Time endTime) {
		DailyAvailability dailyAvailability = new DailyAvailability();
		dailyAvailability.setAvailabilityID(availabilityID);
		dailyAvailability.setDay(day);
		dailyAvailability.setStartTime(startTime);
		dailyAvailability.setEndTime(endTime);
		dailyAvailabilityRepository.save(dailyAvailability);
		return dailyAvailability;
	}
	
	private Appointment createAppointment(String appointmentID, Customer customer, Vehicle vehicle, Technician technician, Invoice invoice, Service service, Set<Timeslot> timeslots) {
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
