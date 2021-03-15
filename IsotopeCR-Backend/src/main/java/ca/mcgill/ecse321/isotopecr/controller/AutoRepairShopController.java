package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.dto.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
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
	@PostMapping(value = { "/resource/delete/{type}", "/resource/delete/{type}/" })
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
	 * @author Mathieu
	 */
	@PostMapping(value = { "/service/get-all", "/service/get-all/" })
	public List<ServiceDto> getAllServices() {
		return autoRepairShopService.getAllServices().stream().map(r -> ControllerHelperMethods.convertToDto(r))
				.collect(Collectors.toList());
	}
	
	/**
	 * @author Mathieu
	 */
	@PostMapping(value = { "/service/create/{servicename}", "/service/create/{servicename}/" })
	public ServiceDto createService(@PathVariable("servicename") String serviceName,
			@PathVariable("duration") Integer duration, @PathVariable("price") Double price,
			@PathVariable("resource") Resource resource, @PathVariable("frequency") Integer frequency) {
		try {
			Service service = (Service) autoRepairShopService.createService(serviceName, duration, price, resource, frequency);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Mathieu
	 */
	@PostMapping(value = { "/service/edit/{servicename}", "/service/edit/{servicename}/" })
	public ServiceDto editService(@PathVariable("servicename") String serviceName,
			@PathVariable("duration") Integer duration, @PathVariable("price") Double price,
			@PathVariable("resource") Resource resource, @PathVariable("frequency") Integer frequency) {
		try {
			Service service = (Service) autoRepairShopService.editService(serviceName, duration, price, resource, frequency);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Mathieu
	 */
	@PostMapping(value = { "/service/delete/{servicename}", "/service/delete/{servicename}" })
	public ServiceDto deleteService(@PathVariable("servicename") String serviceName) {
		try {
			Service service = (Service) autoRepairShopService.deleteService(serviceName);
			return ControllerHelperMethods.convertToDto(service);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

//	/**
//	 * @author Mathieu
//	 */
//	@PostMapping(value = { "/service/get/{servicename}", "/service/get/{servicename}" })
//	public ServiceDto getServiceByName(@PathVariable("servicename") String serviceName) {
//		try {
//			Service service = serviceRepository.findServiceByServiceName(serviceName);
//			return convertToDto(service);
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}

	

	/*********************************************************
	 * Company Profile
	 *********************************************************/

	@PostMapping(value = { "/CompanyProfile/create/{companyprofilename}",
			"/CompanyProfile/create/{companyprofilename}/" })
	public CompanyProfileDto createCompanyProfile(@PathVariable("companyprofilename") String companyName,
			@PathVariable("address") String address, @PathVariable("workinghours") String workingHours) {
		try {
			CompanyProfile companyProfile = autoRepairShopService.createCompanyProfile(companyName, address, workingHours);
			return ControllerHelperMethods.convertToDto(companyProfile);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

//	@PostMapping(value = { "/CompanyProfile/delete/{companyprofilename}",
//			"/CompanyProfile/delete/{companyprofilename}/" })
//	public CompanyProfileDto editCompanyProfile(@PathVariable("companyprofilename") String companyName,
//			@PathVariable("address") String address, @PathVariable("workinghours") String workingHours) {
//		try {
//			CompanyProfile newCompanyProfile = service.createCompanyProfile(companyName, address, workingHours);
//			CompanyProfile oldCompanyProfile = companyProfileRepository.findCompanyProfileByAddress(address);
//			companyProfileRepository.delete(oldCompanyProfile);
//			return convertToDto(newCompanyProfile);
//		} catch (Exception e) {
//			throw e;
//		}
//	}

//	@PostMapping(value = { "/CompanyProfile/get/{address}", "/CompanyProfile/get/{address}" })
//	public CompanyProfileDto getCompanyProfileByAddress(@PathVariable("address") String address) {
//		try {
//			CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileByAddress(address);
//			return convertToDto(companyProfile);
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	@PostMapping(value = { "/CompanyProfile/delete/{address}", "/CompanyProfile/get/{address}" })
//	public void deleteCompanyProfileByAddress(@PathVariable("address") String address) {
//		try {
//			CompanyProfile companyProfile = companyProfileRepository.findCompanyProfileByAddress(address);
//			companyProfileRepository.delete(companyProfile);
//		} catch (Exception e) {
//			throw e;
//		}
//	}

	@PostMapping(value = { "/CompanyProfile/get", "/CompanyProfile/get/" })
	public CompanyProfileDto getCompanyProfile() {
		try {
			List<CompanyProfileDto> companyProfiles = autoRepairShopService.getAllCompanyProfiles().stream()
					.map(cp -> ControllerHelperMethods.convertToDto(cp)).collect(Collectors.toList());
			return companyProfiles.get(0);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/*********************************************************
	 * Summaries TODO
	 *********************************************************/

	// TODO
	
	/**
	 * @author Zichen
	 * @return the total income of the system up to now
	 * @throws Exception
	 */
	@GetMapping(value = { "/invoice/incomesummary", "/invoice/incomesummary/" })
	public double getIncomeSummary() {
		return autoRepairShopService.getIncomeSummary();
	}

	// TODO
	
	/**
	 * @author Zichen
	 * @return the total income of the system up to now
	 * @throws Exception
	 */
	@GetMapping(value = { "/invoice/resourcesummary", "/invoice/resourceummary/" })
	public Map<String, Integer>  getResourceSummary() {
		return autoRepairShopService.getResourceSummary();
	}
	
}
