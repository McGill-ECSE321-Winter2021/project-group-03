package ca.mcgill.ecse321.isotopecr.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import ca.mcgill.ecse321.model.*;

@EntityScan("ca.mcgill.ecse321.model")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestIsotopeCRPersistence {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private CompanyProfileRepository companyProfileRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private TechnicianRepository technicianRepository;
	@Autowired
	private TimeslotRepository timeslotRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;


	@AfterEach
	public void clearDatabase() {
		appointmentRepository.deleteAll();
		companyProfileRepository.deleteAll();
		resourceRepository.deleteAll();
		serviceRepository.deleteAll();
		technicianRepository.deleteAll();
		timeslotRepository.deleteAll();
		customerRepository.deleteAll();
		adminRepository.deleteAll();
		vehicleRepository.deleteAll();
		dailyAvailabilityRepository.deleteAll();
		invoiceRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadAppointment() {
	}

	@Test
	public void testPersistAndLoadCompany() {
	}

	@Test
	public void testPersistAndLoadResource() {
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
		service.setPrice(20.00);
		service.setDuration(duration);
		service.setResource(resource);

		// Save the Service
		serviceRepository.save(service);

		// Create a list of services
		//Set<Service> services = new HashSet<>();
		List<Service> services = new ArrayList<>();
		services.add(service);

		// Test findServiceByName
		service = null;

		service = serviceRepository.findServiceByName(name);
		assertNotNull(service);
		assertEquals(name, service.getName());

		// Test findServiceByResource
		service = null;
	
		List<Service> servicesFound = serviceRepository.findByResource(resource);
		assertNotNull(servicesFound);
		assertEquals(services.size(), servicesFound.size());
//		for(int i = 0; i<servicesFound.size(); i++){
//			assertEquals(servicesFound.get(i).getResource().getResourceType(), resource.getResourceType());
//		}
	}

/*
	@Test
	public void testPersistAndLoadTechnician() {
	}

	@Test
	public void testPersistAndLoadTimeslot() {
	}

	@Test
	public void testPersistAndLoadCustomer() {
	}

	@Test
	public void testPersistAndLoadAdmin() {
	}

	@Test
	public void testPersistAndLoadVehicle() {
	}

	@Test
	public void testPersistAndLoadDailyAvailability() {
	}

	@Test
	public void testPersistAndLoadInvoice() {
	}

//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！ 
	@Test
	public void testPersistAndLoadPerson() {
		String name = "TestPerson";
		// First example for object save/load
		Person person = new Person();
		// First example for attribute save/load
		person.setName(name);
		personRepository.save(person);

		person = null;

		person = personRepository.findPersonByName(name);
		assertNotNull(person);
		assertEquals(name, person.getName());
	}

	@Test
	public void testPersistAndLoadEvent() {
		String name = "ECSE321 Tutorial";
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		Event event = new Event();
		event.setName(name);
		event.setDate(date);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		eventRepository.save(event);

		event = null;

		event = eventRepository.findEventByName(name);

		assertNotNull(event);
		assertEquals(name, event.getName());
		assertEquals(date, event.getDate());
		assertEquals(startTime, event.getStartTime());
		assertEquals(endTime, event.getEndTime());
	}

	@Test
	public void testPersistAndLoadRegistration() {
		String personName = "TestPerson";
		Person person = new Person();
		person.setName(personName);
		personRepository.save(person);

		String eventName = "ECSE321 Tutorial";
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		Event event = new Event();
		event.setName(eventName);
		event.setDate(date);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		eventRepository.save(event);

		Registration reg = new Registration();
		int regId = 1;
		// First example for reference save/load
		reg.setId(regId);
		reg.setPerson(person);
		reg.setEvent(event);
		registrationRepository.save(reg);

		reg = null;

		reg = registrationRepository.findByPersonAndEvent(person, event);
		assertNotNull(reg);
		assertEquals(regId, reg.getId());
		// Comparing by keys
		assertEquals(person.getName(), reg.getPerson().getName());
		assertEquals(event.getName(), reg.getEvent().getName());
	}
	*/
}
