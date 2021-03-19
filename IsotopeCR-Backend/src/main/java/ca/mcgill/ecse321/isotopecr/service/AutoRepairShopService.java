package ca.mcgill.ecse321.isotopecr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;

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

	/*********************************************************
	 * Resource
	 *********************************************************/

	/**
	 * @author Zichen
	 * @param resourceType
	 * @param maxAvailable
	 * @return a resource created by request
	 */
	@Transactional
	public Resource addResource(String resourceType, Integer maxAvailable) {
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
		if (resourceRepository.findResourceByResourceType(resourceType) != null) {
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

	@Transactional
	public Resource getResourceByType(String resourceType) {
		if (resourceRepository.existsById(resourceType)) {
			Resource resource = resourceRepository.findResourceByResourceType(resourceType);
			return resource;
		} else {
			throw new IllegalArgumentException("ERROR: Resource not found in the system.");
		}
	}

	/*********************************************************
	 * Invoice
	 *********************************************************/

	/**
	 * @author Zichen
	 * @return all invoices stored in the system
	 */
	@Transactional
	public List<Invoice> getAllInvoices() {
		List<Invoice> invoices = ServiceHelperMethods.toList(invoiceRepository.findAll());
		return invoices;
	}
	
	/**
	 * Creates an invoice.
	 * @author Mathieu
	 * @param invoiceID - ID of the invoice
	 * @param cost - Cost of the invoice
	 * @param isPaid - Status of the invoice (has it been paid or not?)
	 * @return The created invoice is returned.
	 */
	@Transactional
	public Invoice createInvoice(String invoiceID, double cost, Boolean isPaid) {
		if (ServiceHelperMethods.isValidPrice(cost)) {
			Invoice invoice = new Invoice();
			invoice.setInvoiceID(invoiceID);
			invoice.setCost(cost);
			invoice.setIsPaid(isPaid);
			invoiceRepository.save(invoice);
			return invoice;
		} else {
			throw new IllegalArgumentException("ERROR: Unable to create Invoice, invalid cost.");
		}

	}

	/*********************************************************
	 * Service
	 *********************************************************/

	/**
	 * Creates a service.
	 * @author Mathieu
	 * @param serviceName - Name of the service
	 * @param duration - Duration in minutes of the service, must be a multiple of 30
	 * @param price - Price of the service
	 * @param resource - The resource needed to offer the service
	 * @param frequency - Frequency of the service
	 * @return The created service.
	 */
	@Transactional
	public ca.mcgill.ecse321.isotopecr.model.Service createService(String serviceName, int duration, double price,
			Resource resource, Integer frequency) {

		if (ServiceHelperMethods.isValidServiceName(serviceName) && ServiceHelperMethods.isValidDuration(duration)
				&& ServiceHelperMethods.isValidPrice(price) && ServiceHelperMethods.isValidResource(resource)
				&& ServiceHelperMethods.isValidFrequency(frequency)) {
			ca.mcgill.ecse321.isotopecr.model.Service service = new ca.mcgill.ecse321.isotopecr.model.Service();
			service.setServiceName(serviceName);
			service.setDuration(duration);
			service.setPrice(price);
			service.setResource(resource);
			service.setFrequency(frequency);
			serviceRepository.save(service);
			return service;
		} else {
			throw new IllegalArgumentException("ERROR: Unable to create service.");
		}
	}
	
	/**
	 * @author Mathieu
	 * @param serviceName
	 * @param duration
	 * @param price
	 * @param resource
	 * @param frequency
	 * @return The edited service.
	 */
	@Transactional
	public ca.mcgill.ecse321.isotopecr.model.Service editService(String serviceName, int duration, double price,
			Resource resource, Integer frequency) {
		if (ServiceHelperMethods.isValidServiceName(serviceName) && ServiceHelperMethods.isValidDuration(duration)
				&& ServiceHelperMethods.isValidPrice(price) && ServiceHelperMethods.isValidResource(resource)
				&& ServiceHelperMethods.isValidFrequency(frequency)) {
			ca.mcgill.ecse321.isotopecr.model.Service service = serviceRepository.findServiceByServiceName(serviceName);
			service.setServiceName(serviceName);
			service.setDuration(duration);
			service.setPrice(price);
			service.setResource(resource);
			service.setFrequency(frequency);
			serviceRepository.save(service);
			return service;
		} else {
			throw new IllegalArgumentException("ERROR: Unable to edit service.");
		}
	}
	
	/**
	 * @author Mathieu
	 * @param serviceName
	 * @return The deleted service is returned.
	 */
	@Transactional
	public ca.mcgill.ecse321.isotopecr.model.Service deleteService(String serviceName) {
		if (ServiceHelperMethods.isValidServiceName(serviceName)) {
			ca.mcgill.ecse321.isotopecr.model.Service service = serviceRepository.findServiceByServiceName(serviceName);
			serviceRepository.delete(service);
			return service;
		} else {
			throw new IllegalArgumentException("ERROR: Unable to delete service.");
		}
	}
	
	/**
	 * @author Mathieu
	 * @return A list of all services.
	 */
	@Transactional
	public List<ca.mcgill.ecse321.isotopecr.model.Service> getAllServices() {
		List<ca.mcgill.ecse321.isotopecr.model.Service> services = ServiceHelperMethods
				.toList(serviceRepository.findAll());
		return services;
	}

	/**
	 * @author Mathieu
	 * @param serviceName
	 * @return A searched service
	 */
	@Transactional
	public ca.mcgill.ecse321.isotopecr.model.Service getService(String serviceName) {
		if (serviceName == null) {
			throw new IllegalArgumentException("ERROR: the service name is null.");
		}
		ca.mcgill.ecse321.isotopecr.model.Service service = serviceRepository.findServiceByServiceName(serviceName);
		if (service == null) {
			throw new IllegalArgumentException("ERROR: the service cannot be found.");
		}
		return service;
	}

	/*********************************************************
	 * CompanyProfile
	 *********************************************************/
	
	/**
	 * @author Mathieu
	 * @param companyName - Name of the company
	 * @param address - Address of the company
	 * @param workingHours - Hours during which the company operates
	 * @return The created companyProfile
	 */
	@Transactional
	public CompanyProfile createCompanyProfile(String companyName, String address, String workingHours) {
		if (!getCompanyProfiles().isEmpty()) {
			throw new IllegalArgumentException("ERROR: Company Profile already exists.");
		}
		if (!address.isEmpty()) {
			if (ServiceHelperMethods.isValidCompanyName(companyName)) {
				CompanyProfile companyProfile = new CompanyProfile();
				companyProfile.setCompanyName(companyName);
				companyProfile.setAddress(address);
				companyProfile.setWorkingHours(workingHours);
				companyProfileRepository.save(companyProfile);
				return companyProfile;
			} else {
				throw new IllegalArgumentException("ERROR: Invalid company name.");
			}
		} else {
			throw new IllegalArgumentException("ERROR: Address cannot be empty.");
		}
	}

	/**
	 * @author Mathieu
	 * @param companyName
	 * @param address
	 * @param workingHours
	 * @return The edited companyProfile is returned
	 */
	@Transactional
	public CompanyProfile editCompanyProfile(String companyName, String address, String workingHours) {
		if (!address.isEmpty()) {
			if (ServiceHelperMethods.isValidCompanyName(companyName)) {
				CompanyProfile oldCompanyProfile = companyProfileRepository.findCompanyProfileByAddress(address);
				companyProfileRepository.delete(oldCompanyProfile);

				CompanyProfile newCompanyProfile = new CompanyProfile();
				newCompanyProfile.setCompanyName(companyName);
				newCompanyProfile.setAddress(address);
				newCompanyProfile.setWorkingHours(workingHours);
				companyProfileRepository.save(newCompanyProfile);
				return newCompanyProfile;
			} else {
				throw new IllegalArgumentException("ERROR: Invalid company name.");
			}
		} else {
			throw new IllegalArgumentException("ERROR: Address cannot be empty.");
		}
	}

	/**
	 * @author Mathieu
	 * @return A list of companyProfiles are returned.
	 */
	@Transactional
	public List<CompanyProfile> getCompanyProfiles() {
		List<CompanyProfile> companyProfiles = ServiceHelperMethods.toList(companyProfileRepository.findAll());
		return companyProfiles;
	}
}
