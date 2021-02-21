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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

@EntityScan("ca.mcgill.ecse321.isotopecr.model")
//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestIsotopeCRPersistence {
    @Autowired
	private CompanyProfileRepository companyProfileRepository;

	@Autowired
	private ServiceRepository serviceRepository;
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
		cp.setName(name);
		cp.setWorkingHours(workingHours);
		
		companyProfileRepository.save(cp);
		
		cp=null;
		
		cp=companyProfileRepository.findCompanyProfileByAddress(address);
		assertNotNull(cp);
		assertEquals(address,cp.getAddress());
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
		service.setName(name);
		service.setPrice(price);
		service.setDuration(duration);
		service.setResource(resource);

		// Save the Service
		serviceRepository.save(service);

		// Create a list of services
		//Set<Service> services = new HashSet<>();
//		List<Service> services = new ArrayList<>();
//		services.add(service);

		// Test findServiceByName
		service = null;

		service = serviceRepository.findServiceByName(name);
		assertNotNull(service);
		assertEquals(name, service.getName());
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

		
	//Victoria
	@Test
	public void testPersistAndLoadAppointment() {
	}
//Jiatong
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
//		service.setName(name);
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
//Jack
	@Test
	public void testPersistAndLoadCustomer() {
	}

	@Test
	public void testPersistAndLoadAdmin() {
//		
//			String profileid = "john.doe@mail.com";
//			// First example for object save/load
//			Admin admin = new Admin();
//			// First example for attribute save/load
//			admin.setProfileID(profileid);
//			adminRepository.save(admin);
//
//			admin = null;
//
//			admin = adminRepository.findAdminByProfileID(profileid);
//			assertNotNull(admin);
//			assertEquals(profileid, admin.getEmail());
			
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
}
