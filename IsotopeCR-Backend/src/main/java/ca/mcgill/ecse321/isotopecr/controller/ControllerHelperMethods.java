package ca.mcgill.ecse321.isotopecr.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.isotopecr.dto.AdminDto;
import ca.mcgill.ecse321.isotopecr.dto.AppointmentDto;
import ca.mcgill.ecse321.isotopecr.dto.CompanyProfileDto;
import ca.mcgill.ecse321.isotopecr.dto.CustomerDto;
import ca.mcgill.ecse321.isotopecr.dto.DailyAvailabilityDto;
import ca.mcgill.ecse321.isotopecr.dto.InvoiceDto;
import ca.mcgill.ecse321.isotopecr.dto.ProfileDto;
import ca.mcgill.ecse321.isotopecr.dto.ResourceDto;
import ca.mcgill.ecse321.isotopecr.dto.ServiceDto;
import ca.mcgill.ecse321.isotopecr.dto.TechnicianDto;
import ca.mcgill.ecse321.isotopecr.dto.TimeslotDto;
import ca.mcgill.ecse321.isotopecr.dto.VehicleDto;
import ca.mcgill.ecse321.isotopecr.model.Admin;
import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.CompanyProfile;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Profile;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;

public class ControllerHelperMethods {
	
	/**
	 * @authors Jiatong, Victoria, Zichen, Jack, Mathieu
	 * @param These methods all take in an object of any given class.
	 * @return These methods return the corresponding DTO of the converted object.
	 */

	public static CompanyProfileDto convertToDto(CompanyProfile cp) {
		if (cp == null) {
			throw new IllegalArgumentException("There is no such Company Profile!");
		}
		CompanyProfileDto companyProfileDto = new CompanyProfileDto(cp.getCompanyName(), cp.getAddress(),
				cp.getWorkingHours());
		return companyProfileDto;
	}

	public static ResourceDto convertToDto(Resource r) {
		if (r == null) {
			throw new IllegalArgumentException("There is no such Resource!");
		}
		ResourceDto resourceDto = new ResourceDto(r.getResourceType(), r.getMaxAvailable());
		return resourceDto;
	}

	public static ServiceDto convertToDto(Service s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such Service!");
		}

		ServiceDto serviceDto = new ServiceDto(s.getServiceName(), s.getDuration(), s.getPrice(),
				convertToDto(s.getResource()));
		return serviceDto;
	}

	public static InvoiceDto convertToDto(Invoice i) {
		if (i == null) {
			throw new IllegalArgumentException("There is no such Invoice!");
		}
		InvoiceDto inovoiceDto = new InvoiceDto(i.getCost(), i.getIsPaid(), i.getInvoiceID());
		return inovoiceDto;
	}

	public static DailyAvailabilityDto convertToDto(DailyAvailability d) {
		if (d == null) {
			throw new IllegalArgumentException("There is no such dailyAvailability!");
		}
		DailyAvailabilityDto dailyAvailabilityDto = new DailyAvailabilityDto(d.getAvailabilityID(), d.getStartTime(),
				d.getEndTime(), d.getDay());
		return dailyAvailabilityDto;
	}

	public static CustomerDto convertToDto(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer profile does not exist.");
		}
		CustomerDto customerDto = new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getEmail(),
				customer.getPhoneNumber());
		return customerDto;
	}

	public static VehicleDto convertToDto(Vehicle v) {
		if (v == null) {
			throw new IllegalArgumentException("There is no such Vehicle!");
		}
		VehicleDto vehicleDto = new VehicleDto(v.getLicensePlate(), v.getYear(), v.getModel(), v.getBrand());
		return vehicleDto;
	}

	public static AdminDto convertToDto(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("Administrative account does not exist.");
		}
		AdminDto adminDto = new AdminDto(admin.getFirstName(), admin.getLastName(), admin.getEmail(),
				admin.getIsOwner());
		return adminDto;
	}

	public static TechnicianDto convertToDto(Technician technician) throws Exception {
		if (technician == null) {
			throw new IllegalArgumentException("Technician account does not exist.");
		}
		List<DailyAvailabilityDto> availabilities = technician.getDailyAvailability().stream()
				.map(r -> ControllerHelperMethods.convertToDto(r)).collect(Collectors.toList());
		if (technician.getService() != null) {
			List<ServiceDto> services = technician.getService().stream().map(r -> ControllerHelperMethods.convertToDto(r))
					.collect(Collectors.toList());
			TechnicianDto technicianDto = new TechnicianDto(technician.getFirstName(), technician.getLastName(),
					technician.getEmail(), technician.getPassword(), availabilities);
			technicianDto.setServices(services);
			return technicianDto;
		} else {
			TechnicianDto technicianDto = new TechnicianDto(technician.getFirstName(), technician.getLastName(),
					technician.getEmail(), technician.getPassword(), availabilities);
			return technicianDto;
		}
	}

	public static ProfileDto convertToDto(Profile profile) {
		if (profile == null) {
			throw new IllegalArgumentException("Profile does not exist.");
		}
		ProfileDto profileDto = new ProfileDto(profile.getEmail(), profile.getFirstName(), profile.getLastName());
		return profileDto;
	}

	public static TimeslotDto convertToDto(Timeslot t) {
		if (t == null) {
			throw new IllegalArgumentException("Timeslot does not exist.");
		}

		TimeslotDto timeslotDto = new TimeslotDto(t.getTime(), t.getDate(), t.getSlotID());
		return timeslotDto;
	}

	public static AppointmentDto convertToDto(Appointment a) throws Exception {
		if (a == null) {
			throw new IllegalArgumentException("There is no such appointment!");
		}
		Set<TimeslotDto> timeslots = new HashSet<TimeslotDto>();
		for (Timeslot timeslot : a.getTimeslot()) {
			timeslots.add(convertToDto(timeslot));
		}
		AppointmentDto appointmentDto = new AppointmentDto(a.getAppointmentID(), convertToDto(a.getCustomer()),
				convertToDto(a.getVehicle()), convertToDto(a.getTechnician()), convertToDto(a.getService()), timeslots);
		return appointmentDto;
	}

	public static List<AppointmentDto> convertToDto(List<Appointment> appointments) throws Exception {
		if (appointments == null) {
			throw new IllegalArgumentException("There is no appointments.");
		}
		List<AppointmentDto> aptmtsDto = new ArrayList<AppointmentDto>();
		for (Appointment appointment : appointments) {
			aptmtsDto.add(convertToDto(appointment));
		}
		return aptmtsDto;
	}
}
