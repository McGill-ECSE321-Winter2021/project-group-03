//package ca.mcgill.ecse321.isotopecr.service;
//
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.fail;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.when;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.Month;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.stubbing.Answer;
//
//import ca.mcgill.ecse321.isotopecr.dao.*;
//import ca.mcgill.ecse321.isotopecr.model.*;
//import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;
//import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
//
//@ExtendWith(MockitoExtension.class)
//public class TestIsotopeCRService {
//	@Mock
//	private ResourceRepository resourceRepository;
//	@Mock
//	private AdminRepository adminRepository;
//	@Mock
//	private AppointmentRepository appointmentRepository;
//	@Mock
//	private DailyAvailabilityRepository dailyAvailabilityRepository;
//	
//	@InjectMocks
//	private IsotopeCRService service;
//
//	private static final String RESOURCE_KEY = "TestResource";
//	private static final Integer RESOURCE_MAX = 6;
//	
//	private static final boolean ISREGISTERED = true;
//	/* Mock up Admin */
//	private static final String ADMIN_ID = "admin1";
//	private static final String ADMIN_EMAIL = "admin1@isotopecr.com";
//	private static final String ADMIN_FIRSTNAME = "Peter";
//	private static final String ADMIN_LASTNAME = "Clark";
//	private static final boolean ISOWNER = false;
//	private static final String ADMIN_PASSWORD = "Admin_password1";
//	/* Mock up Technician */
//	private static final String TECH_ID = "tech1";
//	private static final String TECH_EMAIL = "tech1@isotopecr.com";
//	private static final String TECH_FIRSTNAME = "James";
//	private static final String TECH_LASTNAME = "Lan";
//	private static final String TECH_PASSWORD = "Tech_password2";
//	/* Mock up Customer */
//	private static final String CUST_ID = "cust1";
//	private static final String CUST_EMAIL = "cust1@isotopecr.com";
//	private static final String CUST_FIRSTNAME = "MARWAN";
//	private static final String CUST_LASTNAME = "KANAAN";
//	private static final String CUST_PASSWORD = "Cust_password3";
//	private static final String CUST_PHONE = "4831235543";
//	
//	/* Mock up Services */
//	private static final String SERVICE1 = "CarWash";
//	private static final double PRICE1 = 23.33;
//	private static final int FREQUENCY1 = 2;
//	private static final Integer DURATION1 = 5;
//	private static final String SERVICE2 = "TireChange";
//	private static final double PRICE2 = 35.2;
//	private static final int FREQUENCY2 = 1;
//	private static final Integer DURATION2 = 2;
//
//	/* Mock up Resources */
//	private static final String RESOURCE_TYPE1 = "Pull Car";
//	private static final Integer MAX1 = 5;
//	private static final String RESOURCE_TYPE2 = "Scaffold";
//	private static final Integer MAX2 = 2;
//	
//	/* Mock up Vehicles */
//	private static final String LICENSEPLATE1 = "xxxx1";
//	private static final String LICENSEPLATE2 = "xxxx2";
//	private static final String BRAND1 = "Nissan";
//	private static final String BRAND2 = "Volkswagen";
//	private static final String MODEL1 = "GT-R";
//	private static final String MODEL2 = "Tiguan";
//	private static final String YEAR1 = "2016";
//	private static final String YEAR2 = "2018";
//	
//	/* Mock up Invoice */
//	private static final String INVOICE_ID = "12345";
//	private static final double COST = 150;
//	private static final boolean ISPAID = true;
//	
//	/* Mock up DailyAvailabilities */
//	private static final String AVAILABILITY_1 = "1";
//	private static final String AVAILABILITY_2 = "2";
//	private static final String AVAILABILITY_3 = "3";
//	private static final String AVAILABILITY_4 = "4";
//	private static final String AVAILABILITY_5 = "5";
//	
//	/* Mock up Timeslot */
//	private static final Date DATE = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
//	private static final Time TIME = java.sql.Time.valueOf(LocalTime.of(11, 35));
//	private static final String SLOT_ID = "slotID";
//	
//	/* Mock up Appointment */
//	private static final String APPOINTMENT_ID = "appointment1";
//	private static final Status STATUS = Status.BOOKED;
//	
//	
//	private final Customer CUSTOMER = new Customer();
//	private final Vehicle VEHICLE = new Vehicle();
//	private final Technician TECHNICIAN = new Technician();
//	private final Service SERVICE = new Service();
//	
//	/**
//	 * Set up mockoutput when a find method is called to find something inside the database
//	 */
//	@BeforeEach
//	public void setMockOutput() {
//	    lenient().when(resourceRepository.findResourceByResourceType(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
//	        if(invocation.getArgument(0).equals(RESOURCE_KEY)) {
//	            Resource resource = new Resource();
//	            resource.setResourceType(RESOURCE_KEY);
//	            resource.setMaxAvailable(RESOURCE_MAX);
//	            return resource;
//	        } else {
//	            return null;
//	        }
//	    });
//	    
//	   
//	}
//	
//
//
//	@Test
//	public void testCreateResource() {
//		assertEquals(0, service.getAllResources().size());
//		String type = RESOURCE_KEY;
//		Integer max = MAX1;
//		Resource resource = null;
//		try {
//			resource = service.addResource(type, max);
//		} catch (IllegalArgumentException e) {
//			// Check that no error occurred
//			fail(e.getMessage());
//		}
//		assertNotNull(resource);
//		assertEquals(type, resource.getResourceType());
//		assertEquals(max, resource.getMaxAvailable());
//	}
//	
//	@Test
//	public void testCreateResourceTypeNull() {
//		String type = null;
//		Integer max = null;
//		String error = null;
//		Resource resource = null;
//		try {
//			resource = service.addResource(type, max);
//		} catch (IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//		assertNull(resource);
//		// check error
//		assertEquals("ERROR: the resource type can not be empty.", error);
//	}
//	
//	@Test
//	public void testCreateResourceMaxTooSmall() {
//		String type = "PullCar";
//		Integer max = 0;
//		String error = null;
//		Resource resource = null;
//		try {
//			resource = service.addResource(type, max);
//		} catch (IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//		assertNull(resource);
//		// check error
//		assertEquals("ERROR: the resource should at least have one availability.", error);
//	}
//	
//	
//	
//	// ------------------------------ Helpers -------------------------------------
//	private <T> List<T> toList(Iterable<T> iterable) {
//		List<T> resultList = new ArrayList<T>();
//		for (T t : iterable) {
//			resultList.add(t);
//		}
//		return resultList;
//	}
//	
//	private Resource createResource(String type, Integer max) {
//		Resource resource = new Resource();
//		resource.setResourceType(RESOURCE_TYPE1);
//	    resource.setMaxAvailable(MAX1);
//	    return resource;
//	}
//	
//	private Service createService(String name, Resource resource, double price, Integer f, Integer d) {
//		Service service = new Service();
//	    service.setServiceName(name);
//	    service.setResource(resource);
//	    service.setPrice(price);
//	    service.setFrequency(f);
//	    service.setDuration(d);
//	    
//	    return service;
//	}
//	
//	private Vehicle createVehicle(String LicensePlate, String brand, String model, String year) {
//		Vehicle vehicle = new Vehicle();
//		vehicle.setLicensePlate(LicensePlate);
//		vehicle.setBrand(brand);
//		vehicle.setModel(model);
//		vehicle.setYear(year);
//		
//		return vehicle;
//	}
//	
//	private Invoice createInvoice(String id, double cost, boolean ispaid) {
//		Invoice invoice = new Invoice();
//	    invoice.setInvoiceID(id);
//	    invoice.setCost(cost);
//	    invoice.setIsPaid(ispaid);	 
//	    
//	    return invoice;
//	}
//    
//    private Set<DailyAvailability> createSetAvailabilities(){
//    	Set<DailyAvailability> dailyAvailabilities = new HashSet<DailyAvailability>();
//        Integer i = 1;
//    	for (DayOfWeek day : DayOfWeek.values()) {
//    		DailyAvailability dailyAvailability = new DailyAvailability();
//    		dailyAvailability.setDay(day);
//    		dailyAvailability.setStartTime(Time.valueOf(LocalTime.of(9, 00)));
//    		dailyAvailability.setEndTime(Time.valueOf(LocalTime.of(17, 00)));
//    		dailyAvailability.setAvailabilityID((i++).toString());
//    		dailyAvailabilities.add(dailyAvailability);
//    	}
//    	
//    	return dailyAvailabilities;
//    }
//               
//    private Timeslot createTimeslot(Date date, Time time, String id) {
//    	Timeslot slot = new Timeslot();
//    	slot.setDate(date);
//    	slot.setTime(time);
//    	slot.setSlotID(id);
//	    
//	    return slot;
//	}
//    
//    private Technician mockTechnician(Set<DailyAvailability> dailyAvailabilities, Set<Service> services) {
//    	Technician tech = new Technician();
//        tech.setProfileID(TECH_ID);
//        tech.setEmail(ADMIN_EMAIL);
//    	tech.setFirstName(TECH_FIRSTNAME);
//    	tech.setLastName(TECH_LASTNAME);
//    	tech.setIsRegisteredAccount(ISREGISTERED);
//    	tech.setPassword(TECH_PASSWORD);
//    	tech.setDailyAvailability(dailyAvailabilities);
//    	tech.setService(services);
//	    
//	    return tech;
//	}
//    
//    private Customer mockCustomer(Set<Vehicle> vehicles) {
//    	Customer customer = new Customer();
//    	customer.setEmail(CUST_EMAIL);
//    	customer.setFirstName(CUST_FIRSTNAME);
//    	customer.setLastName(CUST_LASTNAME);
//    	customer.setIsRegisteredAccount(ISREGISTERED);
//    	customer.setPassword(CUST_PASSWORD);
//    	customer.setPhoneNumber(CUST_PHONE);
//    	customer.setProfileID(CUST_ID);
//    	customer.setVehicle(vehicles);
//	    
//	    return customer;
//	}
//	
//	
//
//	
//	
//}