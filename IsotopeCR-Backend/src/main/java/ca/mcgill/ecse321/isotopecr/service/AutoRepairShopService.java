package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;

@Service
public class AutoRepairShopService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	VehicleRepository vehicleRepository;
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	@Autowired
	AutoRepairShopRepository autoRepairShopRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	TimeslotRepository timeslotRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	AppointmentRepository appointmentRepository;


	/**
	 * @author Zichen
	 * @param resourceType
	 * @param maxAvailable
	 * @return a resource created by request
	 */
	@Transactional
	public Resource addResource(String resourceType, Integer maxAvailable){
		if (resourceType == null || resourceType.trim().length() == 0) {
			throw new IllegalArgumentException("ERROR: the resource type can not be empty.");
		} else if (resourceRepository.findResourceByResourceType(resourceType) != null) {

			throw new IllegalArgumentException("ERROR: the resource type has existed inside the system.");
		} else if (maxAvailable < 1) {
			throw new IllegalArgumentException("ERROR: the resource should at least have one availability.");
		}
		Resource resource = new Resource();
		resource.setResourceType(resourceType);
		resource.setMaxAvailable(maxAvailable);
		resourceRepository.save(resource);
		return resource;
	}

	/**
	 * @author Zichen
	 * @param resourceType
	 * @return the resource removed by request.
	 */
	@Transactional
	public Resource removeResource(String resourceType) {
		if (resourceRepository.existsById(resourceType)) {
			Resource resource = resourceRepository.findResourceByResourceType(resourceType);
			resourceRepository.delete(resource);
			return resource;
		} else {
			throw new IllegalArgumentException("ERROR: Resource not found in the system.");
		}
	}

	/**
	 * @author Zichen
	 * @return all resources stored in the system
	 */
	@Transactional
	public List<Resource> getAllResources() {
		List<Resource> resources = ServiceHelperMethods.toList(resourceRepository.findAll());
		return resources;
	}

	/**
	 * @author Zichen
	 * @return all invoices stored in the system
	 */
	@Transactional
	public List<Invoice> viewAllInvoices() {
		List<Invoice> invoices = ServiceHelperMethods.toList(invoiceRepository.findAll());
		return invoices;
	}

	/**
	 * @author Zichen
	 * @return the total income by all the appointments upto now
	 */
	@Transactional
	public double viewIncomeSummary() {
		List<Invoice> invoices = ServiceHelperMethods.toList(invoiceRepository.findAll());
		double incomeSummary = 0d;
		for (Invoice i : invoices) {
			if (i.getIsPaid()) {
				incomeSummary += i.getCost();
			}
		}
		return incomeSummary;
	}

	/**
	 * @author Zichen
	 * @return a map indicating how the resources are used.
	 */
	@Transactional
	public Map<String, Integer> viewResourceSummary() {
		List<Resource> resources = ServiceHelperMethods.toList(resourceRepository.findAll());
		Map<String, Integer> resourceAllocation = new HashMap<String, Integer>();

		for (Resource resource : resources) {
			resourceAllocation.put(resource.getResourceType(), 0);
		}

		for (Appointment appointment : appointmentRepository.findAll()) {
			String type = appointment.getService().getResource().getResourceType();
			resourceAllocation.put(type, resourceAllocation.get(type) + 1); // update the usage by 1;
		}

		return resourceAllocation;
	}
	

	
	@Transactional
    public Service addService(String serviceName, int duration, double price, Resource resource) {
    	
    	if(ServiceHelperMethods.isValidServiceName(serviceName) && ServiceHelperMethods.isValidDuration(duration) && ServiceHelperMethods.isValidPrice(price) && ServiceHelperMethods.isValidResource(resource)) {
    		ca.mcgill.ecse321.isotopecr.model.Service service = new ca.mcgill.ecse321.isotopecr.model.Service();
    		service.setServiceName(serviceName);
    		service.setDuration(duration);
    		service.setPrice(price);
    		service.setResource(resource);
    		serviceRepository.save(service);
    		return (Service) service;
    	}else {
			
    		throw new IllegalArgumentException("Inputs.");
		
    	}
    }
    
    @Transactional
    public Service editService(String serviceName, int duration, double price, Resource resource) {
    	
    	if(ServiceHelperMethods.isValidServiceName(serviceName) && ServiceHelperMethods.isValidDuration(duration) && ServiceHelperMethods.isValidPrice(price) && ServiceHelperMethods.isValidResource(resource)) {
    		ca.mcgill.ecse321.isotopecr.model.Service oldService = serviceRepository.findServiceByServiceName(serviceName);
    		serviceRepository.delete(oldService);
    		
    		ca.mcgill.ecse321.isotopecr.model.Service newService = new ca.mcgill.ecse321.isotopecr.model.Service();
    		newService.setServiceName(serviceName);
    		newService.setDuration(duration);
    		newService.setPrice(price);
    		newService.setResource(resource);
    		serviceRepository.save(newService);
    		return (Service) newService;
    		
    	}else {
			
    		throw new IllegalArgumentException("Inputs.");
		
    	}
    }
    
    @Transactional
    public void removeService(String serviceName) {
    	if(ServiceHelperMethods.isValidServiceName(serviceName)) {
    		ca.mcgill.ecse321.isotopecr.model.Service service = serviceRepository.findServiceByServiceName(serviceName);
    		serviceRepository.delete(service);
    		
    	}else {
			
    		throw new IllegalArgumentException("Inputs.");
		
    	}
    }
    
    @Transactional
	public List<ca.mcgill.ecse321.isotopecr.model.Service> viewAllServices() {
		List<ca.mcgill.ecse321.isotopecr.model.Service> services = ServiceHelperMethods.toList(serviceRepository.findAll());
		return services;
	}
    
    @Transactional
    public CompanyProfile createCompanyProfile(String companyName, String address, String workingHours) {
    	if(ServiceHelperMethods.isValidCompanyName(companyName)) {
    		CompanyProfile companyProfile = new CompanyProfile();
    		companyProfile.setCompanyName(companyName);
    		companyProfile.setAddress(address);
    		companyProfile.setWorkingHours(workingHours);
    		companyProfileRepository.save(companyProfile);
    		return companyProfile;
    	}else {
    		throw new IllegalArgumentException("Inputs.");
    	}
    }
    
    @Transactional
    public CompanyProfile editCompanyProfile(String companyName, String address, String workingHours) {
    	if(ServiceHelperMethods.isValidCompanyName(companyName)) {
    		CompanyProfile oldCompanyProfile = companyProfileRepository.findCompanyProfileByAddress(address);
    		companyProfileRepository.delete(oldCompanyProfile);
    		
    		CompanyProfile newCompanyProfile = new CompanyProfile();
    		newCompanyProfile.setCompanyName(companyName);
    		newCompanyProfile.setAddress(address);
    		newCompanyProfile.setWorkingHours(workingHours);
    		companyProfileRepository.save(newCompanyProfile);
    		return newCompanyProfile;
    	}else {
    		throw new IllegalArgumentException("Inputs.");
    	}
    }
    
    @Transactional
	public List<CompanyProfile> viewAllCompanyProfiles() {
		List<CompanyProfile> companyProfiles = ServiceHelperMethods.toList(companyProfileRepository.findAll());
		return companyProfiles;
	}

}
