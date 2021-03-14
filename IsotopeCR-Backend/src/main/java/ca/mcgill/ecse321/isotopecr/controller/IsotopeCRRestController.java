package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import ca.mcgill.ecse321.isotopecr.dto.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.service.IsotopeCRService;


@CrossOrigin(origins = "*")
@RestController
public class IsotopeCRRestController {
	
	@Autowired
	private IsotopeCRService service;
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
	
	
	/**
	 * @author Zichen
	 * @return List of ResourceDtos.
	 */
	@GetMapping(value = { "/resources", "/resources/" })
	public List<ResourceDto> getAllResources() {
		return service.viewAllResources().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
	}
	
	/**
	 * @author Zichen
	 * @param type The resourceType
	 * @param max The maximum availability of one resource
	 * @return A ResourceDto just created
	 */
	@PostMapping(value = { "/events/{type}/{max}", "/events/{type}/{max}" })
	public ResourceDto createResource(@PathVariable("type") String type, @PathVariable("max") Integer max) 
			throws RuntimeException{
		try {
			Resource resource = service.addResource(type, max);
			return convertToDto(resource);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Zichen
	 * @return List of InvoiceDtos
	 */
	@GetMapping(value = { "/invoice", "/invoice/" })
	public List<InvoiceDto> getAllInvoices() {
		return service.viewAllInvoices().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
	}
	
	/**
	 * @author Zichen
	 * @return the total income of the system up to now
	 */
	@GetMapping(value = { "/incomesummary", "/incomesummary/" })
	public double getIncomeSummary() {
		return service.viewIncomeSummary();
	}
	
	@GetMapping(value = { "/availablities/{ID}", "/availabilities/{ID}/" })
	public List<DailyAvailabilityDto> getTechAvailabilities(@PathVariable("ID") String profileID) 
			throws IllegalArgumentException {
		Technician tech = technicianRepository.findTechnicianByProfileID(profileID);
		if (tech == null) {
			throw new IllegalArgumentException("ERROR: Input profileID not found in system.");
		}
		List <DailyAvailabilityDto> dailyAvailabilityDtos = new ArrayList<DailyAvailabilityDto>();
		for (DailyAvailability dailyavailabilities : tech.getDailyAvailability()) {
			dailyAvailabilityDtos.add(convertToDto(dailyavailabilities));
		}
		
		return dailyAvailabilityDtos;
	}
	
	
	/**
	 * @author Zichen
	 * @param profileID profileID stored in database
	 * @return List of DailyAvailabilityDto related to input technician
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/appointment/{vehicle}/{service}", "/appointment/{vehicle}/{service}"})
	public AppointmentDto bookAppointment(@PathVariable("customer") String customerEmail, @PathVariable("vehicle") String licensPlate,
			@PathVariable("technician") String technicianID, @PathVariable("invoice") String invoiceID, @PathVariable("service") String serviceName,
			@RequestParam Time start, @RequestParam Date date) throws IllegalArgumentException {
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensPlate);
		Customer customer = customerRepository.findCustomerByVehicle(vehicle);	
		Invoice invoice = invoiceRepository.findInvoiceByInvoiceID(invoiceID);
		Service serviceInSystem = serviceRepository.findServiceByServiceName(serviceName);
		Technician technician = service.getFreeTechnician(start, date);
		
		service.bookAppointment(customer, vehicle, technician, invoice, serviceInSystem, start, date);
	}
	
	
	/* ============================== Helpers ===============================*/
	private ResourceDto convertToDto(Resource r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Resource!");
		}
		ResourceDto resourceDto = new ResourceDto(r.getResourceType(), r.getMaxAvailable());
		return resourceDto;
	}
	
	private InvoiceDto convertToDto(Invoice i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Invoice!");
		}
		// TODO: complete this one;
//		InvoiceDto inovoiceDto = new InvoiceDto();
//		return invoiceDto;
	}
	
	private DailyAvailabilityDto convertToDto(DailyAvailability d) {
		if (d == null) {
			throw new IllegalArgumentException("There is no such dailyAvailability!");
		}
		DailyAvailabilityDto dailyAvailabilityDto = new DailyAvailabilityDto(d.getAvailabilityID(), d.getStartTime(), d.getEndTime(), d.getDay());
		return dailyAvailabilityDto;
	}

}
