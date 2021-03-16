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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;
@ExtendWith(MockitoExtension.class)
public class TestServiceAndTechnicianService {

	@Mock
	private ResourceRepository resourceRepository;
	
	@Mock
	private ServiceRepository serviceRepository;
	
	@Mock
	private TechnicianRepository technicianRepository;
	
	@Mock 
	private TimeslotRepository timeslotRepository;
	
	@InjectMocks
	private ProfileService profileService;
	
	@InjectMocks
	private AutoRepairShopService autoRepairShopService;
	
	
	private static final String RESOURCE_KEY_1 = "TestResource1";
	private static final Integer RESOURCE_MAX_1 = 6;
	private static final String RESOURCE_KEY_2 = "TestResource2";
	private static final Integer RESOURCE_MAX_2 = 4;
	
	private static final String SERVICE_NAME_1 = "Car Wash";
	private static final Integer SERVICE_DURATION_1 = 45 ;
	private static final int SERVICE_FREQUENCY_1 = 3;
	private static final double SERVICE_PRICE_1 = 5.99;
	private static final String SERVICE_NAME_2 = "Oil change";
	private static final Integer SERVICE_DURATION_2 = 50;
	private static final int SERVICE_FREQUENCY_2 = 3;
	private static final double SERVICE_PRICE_2 = 9.99;
	
	private static final java.sql.Time STARTTIME = java.sql.Time.valueOf(LocalTime.of(15,00));
	private static final java.sql.Date CHOSENDATE = java.sql.Date.valueOf(LocalDate.of(2021, 3, 15));
	private static final String SLOTID = String.valueOf(CHOSENDATE)+String.valueOf(STARTTIME);
	
	
	
	private Resource RESOURCE = new Resource();
	private Appointment APPOINTMENT = new Appointment();
	
	
	@BeforeEach
	public void setMockOutput() {
		lenient().when(resourceRepository.findResourceByResourceType(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(RESOURCE_KEY_1)) {
				Resource resource = new Resource();
				resource.setMaxAvailable(RESOURCE_MAX_1);
				resource.setResourceType(RESOURCE_KEY_1);
				return resource;
			}else {
				return null;
			}
		});
		
		lenient().when(serviceRepository.findServiceByServiceName(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(SERVICE_NAME_1)) {
				Resource resource = new Resource();
				resource.setMaxAvailable(RESOURCE_MAX_1);
				resource.setResourceType(RESOURCE_KEY_1);
				
				Service service = new Service();
				service.setDuration(SERVICE_DURATION_1);
				service.setFrequency(SERVICE_FREQUENCY_1);
				service.setPrice(SERVICE_PRICE_1);
				service.setServiceName(SERVICE_NAME_1);
				service.setResource(resource);
				
				return service;
			
			}else {
				return null;
			}
			
		});
		
		lenient().when(serviceRepository.findServiceByFrequencyGreaterThan(any(int.class))).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(SERVICE_FREQUENCY_1)) {
				Resource resource1 = new Resource();
				resource1.setMaxAvailable(RESOURCE_MAX_1);
				resource1.setResourceType(RESOURCE_KEY_1);
				
				Resource resource2 = new Resource();
				resource2.setMaxAvailable(RESOURCE_MAX_2);
				resource2.setResourceType(RESOURCE_KEY_2);
				
				Service service1 = new Service();
				service1.setDuration(SERVICE_DURATION_1);
				service1.setFrequency(SERVICE_FREQUENCY_1);
				service1.setPrice(SERVICE_PRICE_1);
				service1.setServiceName(SERVICE_NAME_1);
				service1.setResource(resource1);
				
				Service service2 = new Service();
				service2.setDuration(SERVICE_DURATION_2);
				service2.setFrequency(SERVICE_FREQUENCY_1);
				service2.setPrice(SERVICE_PRICE_2);
				service2.setServiceName(SERVICE_NAME_2);
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
			if(invocation.getArgument(0).equals(RESOURCE)) {
	
				
				Service service1 = new Service();
				service1.setDuration(SERVICE_DURATION_1);
				service1.setFrequency(SERVICE_FREQUENCY_1);
				service1.setPrice(SERVICE_PRICE_1);
				service1.setServiceName(SERVICE_NAME_1);
				service1.setResource(RESOURCE);
				
				Service service2 = new Service();
				service2.setDuration(SERVICE_DURATION_2);
				service2.setFrequency(SERVICE_FREQUENCY_2);
				service2.setPrice(SERVICE_PRICE_2);
				service2.setServiceName(SERVICE_NAME_2);
				service2.setResource(RESOURCE);
				
				List<Service> services = new ArrayList<Service>();
				services.add(service1);
				services.add(service2);
				
				return services;
				
			}else {
				return null;
			}
		});
		


		lenient().when(timeslotRepository.findTimeslotBySlotID(anyString())).thenAnswer((InvocationOnMock invocation) ->{
			if(invocation.getArgument(0).equals(SLOTID)) {
				Timeslot timeslot = new Timeslot();
				Set <Appointment> appointments = new HashSet<Appointment>();
				appointments.add(APPOINTMENT);
				
				timeslot.setAppointment(appointments);
				timeslot.setTime(STARTTIME);
				timeslot.setDate(CHOSENDATE);
				timeslot.setSlotID(SLOTID);

				return timeslot;
				
			}else {
				return null;
			}
		});
		

	
		
		
	}
	
}
