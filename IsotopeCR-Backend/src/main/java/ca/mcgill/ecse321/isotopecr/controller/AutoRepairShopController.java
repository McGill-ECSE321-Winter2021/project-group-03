package ca.mcgill.ecse321.isotopecr.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.isotopecr.dao.AdminRepository;
import ca.mcgill.ecse321.isotopecr.dao.AppointmentRepository;
import ca.mcgill.ecse321.isotopecr.dao.AutoRepairShopRepository;
import ca.mcgill.ecse321.isotopecr.dao.CompanyProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.CustomerRepository;
import ca.mcgill.ecse321.isotopecr.dao.DailyAvailabilityRepository;
import ca.mcgill.ecse321.isotopecr.dao.InvoiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.ProfileRepository;
import ca.mcgill.ecse321.isotopecr.dao.ResourceRepository;
import ca.mcgill.ecse321.isotopecr.dao.ServiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.TechnicianRepository;
import ca.mcgill.ecse321.isotopecr.dao.TimeslotRepository;
import ca.mcgill.ecse321.isotopecr.dao.VehicleRepository;
import ca.mcgill.ecse321.isotopecr.dto.CompanyProfileDto;
import ca.mcgill.ecse321.isotopecr.dto.InvoiceDto;
import ca.mcgill.ecse321.isotopecr.dto.ResourceDto;
import ca.mcgill.ecse321.isotopecr.dto.ServiceDto;
import ca.mcgill.ecse321.isotopecr.model.CompanyProfile;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.service.AutoRepairShopService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/autorepairshop")
public class AutoRepairShopController {

	@Autowired
	private AutoRepairShopService autoRepairShopService;
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	@Autowired
	AutoRepairShopRepository autoRepairShopRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	TimeslotRepository timeslotRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	VehicleRepository vehicleRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	/*********************************************************
	 * Resource
	 *********************************************************/

	/**
	 * @author Zichen
	 * @return List of ResourceDtos.
	 */
	@GetMapping(value = { "/resource/get-all", "/resource/get-all/" })
	public List<ResourceDto> getAllResources() {
		return autoRepairShopService.getAllResources().stream().map(r -> ControllerHelperMethods.convertToDto(r))
				.collect(Collectors.toList());
	}

	/**
	 * @author Zichen
	 * @param type The resourceType
	 * @param max  The maximum availability of one resource
	 * @return A ResourceDto just created
	 */
	@PostMapping(value = { "/resource/create/{type}/{max}", "/resource/create/{type}/{max}/" })
	public ResourceDto createResource(@PathVariable("type") String type, @PathVariable("max") Integer max)
			throws RuntimeException {
		try {
			Resource resource = autoRepairShopService.addResource(type, max);
			return ControllerHelperMethods.convertToDto(resource);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Zichen
	 * @return List of ResourceDtos.
	 */
	@DeleteMapping(value = { "/resource/delete/{type}", "/resource/delete/{type}/" })
	public ResourceDto deleteResource(@PathVariable("type") String type) {
		try {
			Resource resource = autoRepairShopService.removeResource(type);
			return ControllerHelperMethods.convertToDto(resource);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*********************************************************
	 * Invoice
	 *********************************************************/

	/**
	 * @author Zichen
	 * @return List of InvoiceDtos
	 */
	@GetMapping(value = { "/invoice/get-all", "/invoice/get-all/" })
	public List<InvoiceDto> getAllInvoices() {
		return autoRepairShopService.getAllInvoices().stream().map(i -> ControllerHelperMethods.convertToDto(i))
				.collect(Collectors.toList());
	}

	/*********************************************************
	 * Service
	 *********************************************************/

	/**
	 * Lists all services.
	 * @author Mathieu
	 * @return All the services offered by the garage
	 */
	@GetMapping(value = { "/service/get-all", "/service/get-all/" })
	public List<ServiceDto> getAllServices() {
		return autoRepairShopService.getAllServices().stream().map(r -> ControllerHelperMethods.convertToDto(r))
				.collect(Collectors.toList());
	}

	/**
	 * Creates a service
	 * @author Mathieu
	 * @param serviceName - Name of the service
	 * @param duration - Duration in minutes of the service, must be a multiple of 30
	 * @param price - Price of the service
	 * @param resourceType - Type of resource the service needs
	 * @param frequency - Frequency of the service
	 * @return The created service
	 */
	@PostMapping(value = { "/service/create/{servicename}", "/service/create/{servicename}/" })
	public ServiceDto createService(@PathVariable("servicename") String serviceName, @RequestParam Integer duration,
			@RequestParam Double price, @RequestParam String resourceType, @RequestParam Integer frequency) {
		try {
			Resource resource = autoRepairShopService.getResourceByType(resourceType);
			Service service = autoRepairShopService.createService(serviceName, duration, price, resource, frequency);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Edits a service.
	 * @author Mathieu
	 * @param serviceName
	 * @param duration
	 * @param price
	 * @param resourceType
	 * @param frequency
	 * @return The edited service is returned
	 */
	@PutMapping(value = { "/service/edit/{servicename}", "/service/edit/{servicename}/" })
	public ServiceDto editService(@PathVariable("servicename") String serviceName, @RequestParam Integer duration,
			@RequestParam Double price, @RequestParam String resourceType, @RequestParam Integer frequency) {
		try {
			Resource resource = autoRepairShopService.getResourceByType(resourceType);
			Service service = (Service) autoRepairShopService.editService(serviceName, duration, price, resource,
					frequency);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Deletes a service.
	 * @author Mathieu
	 * @param serviceName
	 * @return A deleted service is returned
	 */
	@DeleteMapping(value = { "/service/delete/{servicename}", "/service/delete/{servicename}" })
	public ServiceDto deleteService(@PathVariable("servicename") String serviceName) {
		try {
			Service service = (Service) autoRepairShopService.deleteService(serviceName);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * Gets a service by its name.
	 * @author Mathieu
	 * @param serviceName
	 * @return The looked up service is returned.
	 */
	@GetMapping(value = { "/service/get/{servicename}", "/service/get/{servicename}" })
	public ServiceDto getServiceByName(@PathVariable("servicename") String serviceName) {
		try {
			Service service = autoRepairShopService.getService(serviceName);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*********************************************************
	 * Company Profile
	 *********************************************************/
	
	/**
	 * Creates a company profile.
	 * @author Mathieu
	 * @param companyName - Name of the company
	 * @param address - The address of the company
	 * @param workingHours - The working hours of when the company is opened
	 * @return The created company profile is returned. 
	 */

	@PostMapping(value = { "/CompanyProfile/create", "/CompanyProfile/create/" })
	public CompanyProfileDto createCompanyProfile(@RequestParam String companyName, @RequestParam String address,
			@RequestParam String workingHours) {
		try {
			CompanyProfile companyProfile = autoRepairShopService.createCompanyProfile(companyName, address,
					workingHours);
			return ControllerHelperMethods.convertToDto(companyProfile);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * Gets a Company Profile
	 * @author Mathieu
	 * @return The serched company Profile is returned
	 */
	@GetMapping(value = { "/CompanyProfile/get", "/CompanyProfile/get/" })
	public CompanyProfileDto getCompanyProfile() {
		try {
			List<CompanyProfileDto> companyProfiles = autoRepairShopService.getCompanyProfiles().stream()
					.map(cp -> ControllerHelperMethods.convertToDto(cp)).collect(Collectors.toList());
			return companyProfiles.get(0);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * Delete all Company Profiles
	 * @author Zichen
	 * @return The serched company Profile is returned
	 */
	@DeleteMapping(value = { "/CompanyProfile/delete", "/CompanyProfile/delete/" })
	public CompanyProfileDto CompanyProfile() {
		try {
			List<CompanyProfileDto> companyProfiles = autoRepairShopService.deleteCompanyProfiles().stream()
					.map(cp -> ControllerHelperMethods.convertToDto(cp)).collect(Collectors.toList());
			return companyProfiles.get(0);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
