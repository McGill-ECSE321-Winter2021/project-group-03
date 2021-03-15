package ca.mcgill.ecse321.isotopecr.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;

@ExtendWith(MockitoExtension.class)
public class TestIsotopeCRService {
	@Mock
	private ResourceRepository resourceRepository;
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private AppointmentRepository appointmentRepository;
	@Mock
	private DailyAvailabilityRepository dailyAvailabilityRepository;
	
	@InjectMocks
	private IsotopeCRService service;

	private static final String RESOURCE_KEY = "TestResource";
	private static final Integer RESOURCE_MAX = 6;
	
	private static final boolean ISREGISTERED = true;
	/* Mock up Admin */
	private static final String ADMIN_ID = "admin1";
	private static final String ADMIN_EMAIL = "admin1@isotopecr.com";
	private static final String ADMIN_FIRSTNAME = "Peter";
	private static final String ADMIN_LASTNAME = "Clark";
	private static final boolean ISOWNER = false;
	private static final String ADMIN_PASSWORD = "Admin_password1";
	/* Mock up Technician */
	private static final String TECH_ID = "tech1";
	private static final String TECH_EMAIL = "tech1@isotopecr.com";
	private static final String TECH_FIRSTNAME = "James";
	private static final String TECH_LASTNAME = "Lan";
	private static final String TECH_PASSWORD = "Tech_password2";
	/* Mock up Customer */
	private static final String CUST_ID = "cust1";
	private static final String CUST_EMAIL = "cust1@isotopecr.com";
	private static final String CUST_FIRSTNAME = "MARWAN";
	private static final String CUST_LASTNAME = "KANAAN";
	private static final String CUST_PASSWORD = "Cust_password3";
	private static final String CUST_PHONE = "4831235543";
	
	/* Mock up Services */
	private static final String SERVICE1 = "CarWash";
	private static final double PRICE1 = 23.33;
	private static final int FREQUENCY1 = 2;
	private static final Integer DURATION1 = 5;

	/* Mock up Resources */
	private static final String RESOURCE_TYPE1 = "Pull Car";
	private static final Integer MAX1 = 5;
	private static final String RESOURCE_TYPE2 = "Scaffold";
	private static final Integer MAX2 = 2;
	
	/* Mock up Vehicles */
	private static final String LICENSEPLATE1 = "xxxx1";
	private static final String LICENSEPLATE2 = "xxxx2";
	private static final String BRAND1 = "Nissan";
	private static final String BRAND2 = "Volkswagen";
	private static final String MODEL1 = "GT-R";
	private static final String MODEL2 = "Tiguan";
	private static final String YEAR1 = "2016";
	private static final String YEAR2 = "2018";
	
	/* Mock up Invoice */
	private static final String INVOICE_ID = "12345";
	private static final double COST = 150;
	private static final boolean ISPAID = true;
	
	/* Mock up DailyAvailabilities */
	private static final String AVAILABILITY_1 = "1";
	private static final String AVAILABILITY_2 = "2";
	private static final String AVAILABILITY_3 = "3";
	private static final String AVAILABILITY_4 = "4";
	private static final String AVAILABILITY_5 = "5";
	
	/* Mock up Timeslot */
	private static final Date DATE = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
	private static final Time TIME = java.sql.Time.valueOf(LocalTime.of(11, 35));
	private static final String SLOT_ID = "slotID";
	
	/* Mock up Appointment */
	private static final String APPOINTMENT_ID = "appointment1";
	private static final Status STATUS = Status.BOOKED;
	
	/**
	 * Set up mockoutput when a find method is called to find something inside the database
	 */
	@BeforeEach
	public void setMockOutput() {
	    lenient().when(resourceRepository.findResourceByResourceType(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(RESOURCE_KEY)) {
	            Resource resource = new Resource();
	            resource.setResourceType(RESOURCE_KEY);
	            resource.setMaxAvailable(RESOURCE_MAX);
	            return resource;
	        } else {
	            return null;
	        }
	    });
	    
	    /* -------------------------- AdminRepo ------------------------- */
	    /** 
	     * Mock behavior for findAdminByProfileID 
	     */
	    lenient().when(adminRepository.findAdminByProfileID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(ADMIN_ID)) {
	            Admin admin = new Admin();
	            admin.setProfileID(ADMIN_ID);
	            admin.setEmail(ADMIN_EMAIL);
	            admin.setFirstName(ADMIN_ID);
	            admin.setLastName(ADMIN_ID);
	            admin.setIsOwner(ISOWNER);
	            admin.setIsRegisteredAccount(ISREGISTERED);        
	            admin.setPassword(ADMIN_ID);	            
	            return admin;
	        } else {
	            return null;
	        }
	    });
	    
	    
	    /* ---------------------------- AppointmentRepo -------------------------- */
	    /** 
	     * Mock behavior for findAdminByProfileID 
	     */
	    lenient().when(appointmentRepository.findAppointmentByAppointmentID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
	        if(invocation.getArgument(0).equals(ADMIN_ID)) {
	            Resource resource1 = new Resource();
	            Resource resource2 = new Resource();
	            resource1.setResourceType(RESOURCE_TYPE1);
	            resource1.setMaxAvailable(MAX1);
	            resource2.setResourceType(RESOURCE_TYPE2);
	            resource2.setMaxAvailable(MAX2);
	        	
	        	Service service1 = new Service();
	            Service service2 = new Service();
	            service1.setServiceName(SERVICE1);
	            service1.setResource(resource1);
	            service1.setPrice(PRICE1);
	            service1.setFrequency(FREQUENCY1);
	            service1.setDuration(DURATION1);	
	            service2.setServiceName(ADMIN_EMAIL);
	            service2.setResource(resource2);
	            service2.setPrice(0);
	            service2.setFrequency(0);
	            service2.setDuration(RESOURCE_MAX);
	            Set<Service> services = new HashSet<Service>();
	            services.add(service1);
	            services.add(service2);
	            
	            Vehicle vehicle1 = new Vehicle();
	            Vehicle vehicle2 = new Vehicle();
	            vehicle1.setLicensePlate(LICENSEPLATE1);
	            vehicle1.setBrand(BRAND1);
	            vehicle1.setModel(MODEL1);
	            vehicle1.setYear(YEAR1);
	            vehicle2.setLicensePlate(LICENSEPLATE2);
	            vehicle2.setBrand(BRAND2);
	            vehicle2.setModel(MODEL2);
	            vehicle2.setYear(YEAR2);
	            Set<Vehicle> vehicles = new HashSet<Vehicle>();
	            vehicles.add(vehicle1);
	            vehicles.add(vehicle2);
	            
	            Invoice invoice = new Invoice();
	            invoice.setInvoiceID(INVOICE_ID);
	            invoice.setCost(COST);
	            invoice.setIsPaid(ISPAID);	            
	            
	            Set<DailyAvailability> dailyAvailabilities = new HashSet<DailyAvailability>();
	            Integer i = 1;
	    		for (DayOfWeek day : DayOfWeek.values()) {
	    			DailyAvailability dailyAvailability = new DailyAvailability();
	    			dailyAvailability.setDay(day);
	    			dailyAvailability.setStartTime(Time.valueOf(LocalTime.of(9, 00)));
	    			dailyAvailability.setEndTime(Time.valueOf(LocalTime.of(17, 00)));
	    			dailyAvailability.setAvailabilityID((i++).toString());
	    			dailyAvailabilities.add(dailyAvailability);
	    		}
	    		
	    		Timeslot slot = new Timeslot();
	    		slot.setDate(DATE);
	    		slot.setTime(TIME);
	    		slot.setSlotID(SLOT_ID);
	    		Set<Timeslot> slots = new HashSet<Timeslot>();
	    		slots.add(slot);
	            
	            Technician tech = new Technician();
	            tech.setProfileID(TECH_ID);
	            tech.setEmail(ADMIN_EMAIL);
	        	tech.setFirstName(TECH_FIRSTNAME);
	        	tech.setLastName(TECH_LASTNAME);
	        	tech.setIsRegisteredAccount(ISREGISTERED);
	        	tech.setPassword(TECH_PASSWORD);
	        	tech.setDailyAvailability(dailyAvailabilities);
	        	tech.setService(services);
	        	
	        	Customer customer = new Customer();
	        	customer.setEmail(CUST_EMAIL);
	        	customer.setFirstName(CUST_FIRSTNAME);
	        	customer.setLastName(CUST_LASTNAME);
	        	customer.setIsRegisteredAccount(ISREGISTERED);
	        	customer.setPassword(CUST_PASSWORD);
	        	customer.setPhoneNumber(CUST_PHONE);
	        	customer.setProfileID(CUST_ID);
	        	customer.setVehicle(vehicles);
	        	
	        	Appointment appointment = new Appointment();
	        	Set<Appointment> appointments = new HashSet<Appointment>();	
	        	appointments.add(appointment);
	        	slot.setAppointment(appointments);
	            appointment.setAppointmentID(APPOINTMENT_ID);
	            appointment.setStatus(STATUS);
	            appointment.setInvoice(invoice);
	            appointment.setService(service1);	            
	            appointment.setTechnician(tech);
	            appointment.setCustomer(customer);
	            appointment.setTimeslot(slots);
	            appointment.setVehicle(vehicle1);
	            return appointment;
	        } else {
	            return null;
	        }
	    });
	}
	
	@Test
	public void testCreateResource() {
		assertEquals(0, service.getAllResources().size());
		String type = RESOURCE_KEY;
		Integer max = MAX1;
		Resource resource = null;
		try {
			resource = service.addResource(type, max);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail(e.getMessage());
		}
		assertNotNull(resource);
		assertEquals(type, resource.getResourceType());
		assertEquals(max, resource.getMaxAvailable());
	}
	
	@Test
	public void testCreateResourceTypeNull() {
		String type = null;
		Integer max = null;
		String error = null;
		Resource resource = null;
		try {
			resource = service.addResource(type, max);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(resource);
		// check error
		assertEquals("ERROR: the resource type can not be empty.", error);
	}
	
	@Test
	public void testCreateResourceMaxTooSmall() {
		String type = "PullCar";
		Integer max = 0;
		String error = null;
		Resource resource = null;
		try {
			resource = service.addResource(type, max);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(resource);
		// check error
		assertEquals("ERROR: the resource should at least have one availability.", error);
	}
	
	

}