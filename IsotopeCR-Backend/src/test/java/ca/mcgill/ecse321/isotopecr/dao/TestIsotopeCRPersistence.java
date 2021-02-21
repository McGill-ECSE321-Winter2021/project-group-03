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

import ca.mcgill.ecse321.isotopecr.model.*;

@EntityScan("ca.mcgill.ecse321.isotopecr.model")
//@ExtendWith(SpringExtension.class)
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
		List<Service> services = new ArrayList<>();
		services.add(service);

		// Test findServiceByName
		service = null;

		service = serviceRepository.findServiceByName(name);
		assertNotNull(service);
		assertEquals(name, service.getName());
		assertEquals(resource.getResourceType(),service.getResource().getResourceType());

		// Test findServiceByResource
		service = null;
	
		List<Service> servicesFound = serviceRepository.findByResource(resource);
		assertNotNull(servicesFound);
		assertEquals(services.size(), servicesFound.size());
		for(int i = 0; i<servicesFound.size(); i++){
			assertEquals(servicesFound.get(i).getResource().getResourceType(), resource.getResourceType());
		}
	}

/*
	@Test
	public void testPersistAndLoadAppointment() {
	}

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

*/
}
