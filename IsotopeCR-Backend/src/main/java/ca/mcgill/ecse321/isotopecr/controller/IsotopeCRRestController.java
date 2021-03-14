package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
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
	
	
	/* ----------------------- Zichen --------------------------*/
	@GetMapping(value = { "/resources", "/resources/" })
	public List<ResourceDto> getAllResources() {
		return service.viewAllResources().stream().map(r -> convertToDto(r)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/events/{type}/{max}", "/events/{type}/{max}" })
	public ResourceDto createResource(@PathVariable("type") String type, @PathVariable("max") Integer max)
	throws IllegalArgumentException {
		// check the input validity first:
		if (resourceRepository.findResourceByResourceType(type) != null) {
			throw new IllegalArgumentException("ERROR: the resource type has existed inside the system.");
		} else if (max < 1) {
			throw new IllegalArgumentException("ERROR: the resource should at least have one availability.");
		}
		Resource resource = service.addResource(type, max);
		return convertToDto(resource);
	}

	@GetMapping(value = { "/invoice", "/invoice/" })
	public List<InvoiceDto> getAllInvoices() {
		return service.viewAllInvoices().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/incomesummary", "/incomesummary/" })
	public double getIncomeSummary() {
		return service.viewIncomeSummary();
	}
	
	@GetMapping(value = { "/availablities/{ID}", "/availabilities/{name}/" })
	public List<DailyAvailabilityDto> getTechAvailabilities(@PathVariable("name") String profileID) throws IllegalArgumentException {
		Technician tech = technicianRepository.findTechnicianByProfileID(profileID);
		tech.getDailyAvailability();
//		service.viewAvailability(name)	
//		Person person = service.createPerson(name);
//		return convertToDto(person);
	}
	
	@GetMapping (value = {"/appointment/{vehicle}/{startTime}","/appointment/{vehicle}/{startTime}"})
	
	
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
			throw new IllegalArgumentException("There is no such Invoice!");
		}
		DailyAvailabilityDto dailyAvailabilityDto = new DailyAvailabilityDto(d.getAvailabilityID(), d.getStartTime(), d.getEndTime(), d.getDay());
		return dailyAvailabilityDto;
	}

}
