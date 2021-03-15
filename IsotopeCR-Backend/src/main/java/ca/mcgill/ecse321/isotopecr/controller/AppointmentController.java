package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import ca.mcgill.ecse321.isotopecr.service.AppointmentService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
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
	 * @param profileID profileID stored in database
	 * @return List of DailyAvailabilityDto related to input technician
	 * @throws Exception 
	 */

	@PostMapping(value = { "/create/{vehicle}/{service}", "/create/{vehicle}/{service}" })
	public AppointmentDto createAppointment(@PathVariable("vehicle") String licensePlate,
			@PathVariable("service") String serviceName,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm:ss") LocalTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate date)
			throws Exception {
		
		Time startTime = Time.valueOf(start);
		Date appointmentDate = Date.valueOf(date);
		Technician technician = appointmentService.getFreeTechnician(startTime, appointmentDate);
		
		Appointment appointment = appointmentService.createAppointment(customer, vehicle, technician, serviceInSystem, startTime,
				appointmentDate);
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		Customer customer = customerRepository.findCustomerByVehicle(vehicle);
		Service serviceInSystem = serviceRepository.findServiceByServiceName(serviceName);

		
		

		Appointment appointment = service.bookAppointment(customer, vehicle, technician, serviceInSystem, startTime,
				appointmentDate);
		return convertToDto(appointment);
	}

	@GetMapping(value = { "/pastappointment/customer/{customer}", "/pastappointment/customer/{customer}/" })
	public List<AppointmentDto> viewPastAppointmentForCustomer(@PathVariable("customer") String email)
			throws Exception {
		try {
			Profile aCustomer = service.getProfile(email);
			if (!(aCustomer instanceof Customer)) {
				throw new IllegalArgumentException("This is not a customer account.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByCustomer((Customer) aCustomer);

			appointments = service.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/futureappointment/customer/{customer}", "/futureappointment/customer/{customer}/" })
	public List<AppointmentDto> viewFutureAppointmentForCustomer(@PathVariable("customer") String email)
			throws Exception {
		try {
			Profile aCustomer = service.getProfile(email);
			if (!(aCustomer instanceof Customer)) {
				throw new IllegalArgumentException("This is not a customer account.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByCustomer((Customer) aCustomer);

			appointments = service.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/pastappointment/vehicle/{vehicle}", "/pastappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewPastAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) throws Exception {
		try {
			Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
			if (vehicle == null) {
				throw new IllegalArgumentException("There is no such vehicle in the system.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByVehicle(vehicle);

			appointments = service.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/futureappointment/vehicle/{vehicle}", "/futureappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewFutureAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) throws Exception {
		try {
			Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
			if (vehicle == null) {
				throw new IllegalArgumentException("There is no such vehicle in the system.");
			}
			List<Appointment> appointments = appointmentRepository.findAppointmentByVehicle(vehicle);

			appointments = service.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}

			return convertToDto(uncancelledappointments);
		} catch (Exception e) {
			throw e;
		}
	}

	@PostMapping(value = { "/cancelappointment/{appointment}", "/cancelappointment/{appointment}/" })
	public AppointmentDto cancelAppointment(@PathVariable("appointment") String aAppointmentId) throws Exception {
		try {
			Appointment appointment = appointmentRepository.findAppointmentByAppointmentID(aAppointmentId);

			appointment = service.cancelAppointment(appointment);

			return convertToDto(appointment);
		} catch (Exception e) {
			throw e;
		}
	}

}
