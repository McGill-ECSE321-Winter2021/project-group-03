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
	public Customer createCustomer(String firstName, String lastName) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);

		// TODO: finish all the fields for customer

		customerRepository.save(customer);
		return customer;
	}
	
	@Transactional
	public void updateAvailability(Technician tech, String day, Time startTime, 
			Time endTime) {
		List<DailyAvailability> availabilities = toList(tech.getDailyAvailability());
		for(DailyAvailability da : availabilities) {
			if (da.getDay().toString().equals(day)) {
				da.setStartTime(startTime);
				da.setEndTime(endTime);
				return;
			}
		}
		System.out.println("Input day is invalid, please retry.");
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
	public double viewSummary() {
		// TODO: want to see the income summation / resource allocation.
		List<Invoice> invoices = toList(invoiceRepository.findAll());
		double incomeSummary = 0d;
		for (Invoice i : invoices) {
			incomeSummary += i.getCost();
		}
		return incomeSummary;
	}
	
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
