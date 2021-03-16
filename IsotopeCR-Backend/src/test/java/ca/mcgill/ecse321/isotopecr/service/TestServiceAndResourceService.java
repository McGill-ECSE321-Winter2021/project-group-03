package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.AppointmentRepository;
import ca.mcgill.ecse321.isotopecr.dao.ResourceRepository;
import ca.mcgill.ecse321.isotopecr.dao.ServiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.TechnicianRepository;
import ca.mcgill.ecse321.isotopecr.dao.TimeslotRepository;
import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;;

@ExtendWith(MockitoExtension.class)
public class TestServiceAndResourceService {
	
	@Mock
	private ServiceRepository serviceRepository;
	@Mock
	private ResourceRepository resourceRepository;

	@InjectMocks
	private AutoRepairShopService autoRepairShopService;

	/* Mock up Services */
	//Service 1
	private static final String SERVICE_NAME1 = "CarWash";
	private static final double PRICE1 = 25.50;
	private static final int FREQUENCY1 = 3;
	private static final Integer DURATION1 = 150;
	//Service 2
	private static final String SERVICE_NAME2 = "TireChange";
	private static final double PRICE2 = 45.5;
	private static final int FREQUENCY2 = 2;
	private static final Integer DURATION2 = 60;
	//Service 3
	private static final String SERVICE_NAME3 = "OilChange";
	private static final double PRICE3 = 35.45;
	private static final int FREQUENCY3 = 1;
	private static final Integer DURATION3 = 30;

	/* Mock up Resources */
	//Resource 1
	private static final String RESOURCE_TYPE1 = "PullCar";
	private static final Integer MAX1 = 4;
	//Resource 2
	private static final String RESOURCE_TYPE2 = "Scaffolding";
	private static final Integer MAX2 = 3;
	//Resource 3
	private static final String RESOURCE_TYPE3 = "CarTires";
	private static final Integer MAX3 = 8;
	
	//Resource 4
	private static final String RESOURCE_TYPE4 = "CarEngine";
	private static final Integer MAX4 = 1;
	//Resource 5
	private static final String RESOURCE_TYPE5 = "CarWindShield";
	private static final Integer MAX5 = 2;
	//Resource 6
	private static final String RESOURCE_TYPE6 = "Spaces";
	private static final Integer MAX6 = 2;
	
	private final Service SERVICE = new Service();
	private final Resource RESOURCE1 = new Resource();
	private final Resource RESOURCE2 = new Resource();
	
	@BeforeEach
	public void setMockOutput() {
		
		lenient().when(resourceRepository.findResourceByResourceType(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(RESOURCE_TYPE1)) {
				Resource resource = new Resource();
				resource.setMaxAvailable(MAX1);
				resource.setResourceType(RESOURCE_TYPE1);
				return resource;
			}else {
				return null;
			}
		});
		
		lenient().when(serviceRepository.findServiceByServiceName(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(SERVICE_NAME1)) {
				Resource resource = new Resource();
				resource.setMaxAvailable(MAX1);
				resource.setResourceType(RESOURCE_TYPE1);
				
				Service service = new Service();
				service.setDuration(DURATION1);
				service.setFrequency(FREQUENCY1);
				service.setPrice(PRICE1);
				service.setServiceName(SERVICE_NAME1);
				service.setResource(resource);
				
				return service;
			
			}else {
				return null;
			}
			
		});
		
		lenient().when(serviceRepository.findServiceByFrequencyGreaterThan(any(int.class))).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(FREQUENCY1)) {
				Resource resource1 = new Resource();
				resource1.setMaxAvailable(MAX1);
				resource1.setResourceType(RESOURCE_TYPE1);
				
				Resource resource2 = new Resource();
				resource2.setMaxAvailable(MAX2);
				resource2.setResourceType(RESOURCE_TYPE2);
				
				Service service1 = new Service();
				service1.setDuration(DURATION1);
				service1.setFrequency(FREQUENCY1);
				service1.setPrice(PRICE1);
				service1.setServiceName(SERVICE_NAME1);
				service1.setResource(resource1);
				
				Service service2 = new Service();
				service2.setDuration(DURATION2);
				service2.setFrequency(FREQUENCY2);
				service2.setPrice(PRICE2);
				service2.setServiceName(SERVICE_NAME2);
				service2.setResource(resource2);
				
				List<Service> services = new ArrayList<Service>();
				services.add(service1);
				services.add(service2);
				
				return services;
				
			}else {
				return null;
			}
		});
		

		lenient().when(serviceRepository.findServiceByResource(any(Resource.class))).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(RESOURCE1)) {
	
				
				Service service1 = new Service();
				service1.setDuration(DURATION1);
				service1.setFrequency(FREQUENCY1);
				service1.setPrice(PRICE1);
				service1.setServiceName(SERVICE_NAME1);
				service1.setResource(RESOURCE1);
				
				Service service2 = new Service();
				service2.setDuration(DURATION2);
				service2.setFrequency(FREQUENCY2);
				service2.setPrice(PRICE2);
				service2.setServiceName(SERVICE_NAME2);
				service2.setResource(RESOURCE1);
				
				Service service3 = new Service();
				service2.setDuration(DURATION3);
				service2.setFrequency(FREQUENCY3);
				service2.setPrice(PRICE3);
				service2.setServiceName(SERVICE_NAME3);
				service2.setResource(RESOURCE2);
				
				List<Service> services = new ArrayList<Service>();
				services.add(service1);
				services.add(service2);
				services.add(service3);
				
				return services;
				
			}else {
				return null;
			}
		});
		
	}
		
		@Test
		public void testAddResource() {
				Resource resource = null;
			
			try {
			    resource = autoRepairShopService.addResource(RESOURCE_TYPE3, MAX3);
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			
			assertNotNull(resource);
			assertEquals(RESOURCE_TYPE3, resource.getResourceType());
			assertEquals(MAX3, resource.getMaxAvailable());

		}
		
		//Doesn't work yet
		@Test
		public void testRemoveResource() {
			
			Resource resource = new Resource();
			resource.setResourceType(RESOURCE_TYPE6);
			resource = autoRepairShopService.addResource(RESOURCE_TYPE6, MAX6);
			resourceRepository.save(resource);
			
			Resource removedResource = null;
			
			try {
				removedResource = autoRepairShopService.removeResource(RESOURCE_TYPE6);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		
			assertNotNull(removedResource);

			
		}
		
		
		@Test
		public void testGetAllResources() {
			
			Resource resource1 = new Resource();
			Resource resource2 = new Resource();
			
			resource1 = autoRepairShopService.addResource(RESOURCE_TYPE4, MAX4);
			resource2 = autoRepairShopService.addResource(RESOURCE_TYPE5, MAX5);
			
			List<Resource> resources = autoRepairShopService.getAllResources();
			resources.add(resource1);
			resources.add(resource2);
			
			assertNotNull(resources);
			assertEquals(2, resources.size());
			assertEquals(RESOURCE_TYPE4, resources.get(0).getResourceType());
			assertEquals(RESOURCE_TYPE5, resources.get(1).getResourceType());
			
			
		}
	
	
		@Test
		public void testCreateService() {
			Service service = null;
			Resource resource = new Resource();
			
			resource.setMaxAvailable(MAX1);
			resource.setResourceType(RESOURCE_TYPE1);
			
			try {
			    service = autoRepairShopService.createService(SERVICE_NAME1,DURATION1, PRICE1, resource, FREQUENCY1);
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			
			assertNotNull(service);
			assertEquals(SERVICE_NAME1,service.getServiceName());
			assertEquals(DURATION1,service.getDuration());
			assertEquals(PRICE1,service.getPrice());
			assertEquals(FREQUENCY1,service.getFrequency());
			assertEquals(MAX1,service.getResource().getMaxAvailable());
			assertEquals(RESOURCE_TYPE1,service.getResource().getResourceType());
			
		}
		
		@Test
		public void testEditService() {
			Service service = null;
			Resource resource = new Resource();
			
			resource.setMaxAvailable(MAX1);
			resource.setResourceType(RESOURCE_TYPE1);
			
			try {
				service = autoRepairShopService.createService(SERVICE_NAME1,DURATION1, PRICE1, resource, FREQUENCY1);
			    service = autoRepairShopService.editService(SERVICE_NAME1,DURATION1, PRICE1, resource, FREQUENCY2);
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			
			assertNotNull(service);
			assertEquals(SERVICE_NAME1,service.getServiceName());
			assertEquals(DURATION1,service.getDuration());
			assertEquals(PRICE1,service.getPrice());
			assertEquals(FREQUENCY2,service.getFrequency());
			assertEquals(MAX1,service.getResource().getMaxAvailable());
			assertEquals(RESOURCE_TYPE1,service.getResource().getResourceType());
			
		}
		
		@Test
		public void testDeleteService() {	
			Service service = new Service();
			service.setServiceName(SERVICE_NAME1);
			
			Service deletedService = null;
			
			
			try {
				deletedService = autoRepairShopService.deleteService(SERVICE_NAME1);
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
			
			assertNotNull(deletedService);	
		}
		
		@Test
		public void getAllServices() {	
			Service service1 = null;
			Service service2 = null;
			Resource resource = new Resource();
			
			resource.setMaxAvailable(MAX1);
			resource.setResourceType(RESOURCE_TYPE1);
			
			service1 = autoRepairShopService.createService(SERVICE_NAME1,DURATION1, PRICE1, resource, FREQUENCY1);
			service2 = autoRepairShopService.createService(SERVICE_NAME2,DURATION2, PRICE2, resource, FREQUENCY2);
			
			List<Service> services = autoRepairShopService.getAllServices();
			
			services.add(service1);
			services.add(service2);
			
			assertNotNull(services);
			assertEquals(2, services.size());
			assertEquals(SERVICE_NAME1, services.get(0).getServiceName());
			assertEquals(SERVICE_NAME2, services.get(1).getServiceName());

		}
		
}
			
	
	/* ------------------------------ Helpers ------------------------------------- */
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
//}
	
//	private Vehicle createVehicle(String LicensePlate, String brand, String model, String year) {
//		Vehicle vehicle = new Vehicle();
//		vehicle.setLicensePlate(LicensePlate);
//		vehicle.setBrand(brand);
//		vehicle.setModel(model);
//		vehicle.setYear(year);
//		
//		return vehicle;
//	}
	
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
    
//    private Technician mockTechnician(Set<DailyAvailability> dailyAvailabilities, Set<Service> services) {
//    	Technician tech = new Technician();
//        tech.setProfileID(TECH_ID);
//        tech.setEmail(TECH_EMAIL);
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
	
