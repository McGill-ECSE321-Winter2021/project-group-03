package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

public class IsotopeCRService {
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

	
	@Transactional
	public void updateAvailability(Technician tech, DayOfWeek day, Time startTime, 
			Time endTime) {
		List<DailyAvailability> availabilities = toList(tech.getDailyAvailability());
		for(DailyAvailability availability : availabilities) {
			if (availability.getDay().equals(day)) {
				availability.setStartTime(startTime);
				availability.setEndTime(endTime);
				return;
			}
		}
		System.out.println("Input day is invalid, please retry.");	// TODO where to check the input
	}
	
	@Transactional
	public List<DailyAvailabilityDto> viewAvailability(Technician tech){
		// TODO: return technician with his/her availabilities
		List<DailyAvailabilityDto> availabilities = toList(tech.getDailyAvailability());
	}

	
	@Transactional
	public Resource addResource(String resourceType, Integer maxAvailable) {
		Resource resource = new Resource();
		resource.setResourceType(resourceType);
		resource.setMaxAvailable(maxAvailable);

		resourceRepository.save(resource);
		return resource;
	}
	
	@Transactional
	public Resource removeResource(String resourceType) {
		if (resourceRepository.existsById(resourceType)) {
			Resource resource = resourceRepository.findResourceByResourceType(resourceType);
			resourceRepository.delete(resource);
			return resource;
		} else {
			System.out.println("Input not found in the database.");
			return null;
		}
	}
	
	
	@Transactional
	public double viewIncomeSummary() {
		// TODO: want to see the income summation / resource allocation.
		List<Invoice> invoices = toList(invoiceRepository.findAll());
		double incomeSummary = 0d;
		for (Invoice i : invoices) {
			incomeSummary += i.getCost();
		}
		return incomeSummary;
	}
	
	@Transactional
	public void viewResourceSummary() {
		// TODO: want to see the resource allocation.
		
	}
	
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
