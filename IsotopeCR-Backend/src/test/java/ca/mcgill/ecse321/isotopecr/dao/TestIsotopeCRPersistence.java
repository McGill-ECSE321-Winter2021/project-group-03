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
		
		autoRepairShopRepository.deleteAll();
		profileRepository.deleteAll();
		appointmentRepository.deleteAll();
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
	
	//Jack
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
//	@Test
//	public void testPersistAndLoadTechnician() {
//		
//		DayOfWeek day= DayOfWeek.Tuesday;
//		java.sql.Time startTime= java.sql.Time.valueOf(LocalTime.of(11, 35));
//		java.sql.Time endTime =  java.sql.Time.valueOf(LocalTime.of(13, 25));
//		String availabilityID ="availabilityID";
//		
//		DailyAvailability dA = new DailyAvailability();
//		dA.setAvailabilityID(availabilityID);
//		dA.setDay(day);
//		dA.setStartTime(startTime);
//		dA.setEndTime(endTime);
//
//	
//		
//		Set<DailyAvailability> dAs = new HashSet<DailyAvailability>();
//		dAs.add(dA);
//			
//		dailyAvailabilityRepository.save(dA);
//		
//		String type = "Resource Type";
//		int maxAvailable = 4;
//		
//		Resource resource = new Resource();
//		resource.setResourceType(type);
//		resource.setMaxAvailable(maxAvailable);
//		resourceRepository.save(resource);
//
//		// Create a Service
//		String name = "Service Name";
//		double price = 20.00;
//		int duration = 3;
//
//		Service service = new Service();
//		service.setServiceName(name);
//		service.setPrice(price);
//		service.setDuration(duration);
//		service.setResource(resource);
//
//		// Save the Service
//		
//		
//		Set<Service> services = new HashSet<Service>();
//		services.add(service);
//		
//		serviceRepository.save(service);
//		
//		
//		String firstName ="firstName";
//		String lastName ="lastName";
//		String email ="email";
//		String password ="password";
//		boolean isRegisteredAccount = true;
//		String profileID = "profileID";
//		
//		Technician t = new Technician();
//		t.setProfileID(profileID);
//		t.setEmail(email);
//		t.setFirstName(firstName);
//		t.setLastName(lastName);
//		t.setIsRegisteredAccount(isRegisteredAccount);
//		t.setPassword(password);
//		//t.setServices(services);
//		//t.setDailyAvailabilities(dAs);
//		technicianRepository.save(t);
//		
//		t = null;
//		
//		t=technicianRepository.findTechnicianByProfileID(profileID);
//		assertNotNull(t);
//		assertEquals(t.getProfileID(),profileID);
//		
//		
//		
//	}
/*		
	//Victoria
	@Test
	public void testPersistAndLoadAppointment() {
	}
//Jiatong



	/*
//Jack
	@Test
	public void testPersistAndLoadCustomer() {
	}



//Zichen
	@Test
	public void testPersistAndLoadVehicle() {
	}

	@Test
	public void testPersistAndLoadDailyAvailability() {
	}
//Mathieu
	@Test
	public void testPersistAndLoadInvoice() {
		
//	Invoice invoice = new Invoice();
//
//	  
//	  String invoiceID = "12345";
//	  double cost = 150;
//	  boolean isPaid = true;
//	  
//	  
//	  invoice.setInvoiceID(invoiceID);
//	  invoice.setCost(cost);
//	  invoice.setIsPaid(isPaid);
//	  //invoice.setAppointment(appointment);
//	  invoiceRepository.save(invoice);
//	  
//	  invoice = null;
//	  
//	  
//	  invoice = invoiceRepository.findInvoiceByInvoiceID(invoiceID);
//	  assertNotNull(invoice);
//	  assertEquals(invoiceID, invoice.getInvoiceID());
//	  //assertEquals(appointment.getAppointmentID(), invoice.getAppointment().getAppointmentID());
	}
	*/
}
