package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import ca.mcgill.ecse321.isotopecr.dto.AppointmentDto;
import ca.mcgill.ecse321.isotopecr.dto.InvoiceDto;
import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;
import ca.mcgill.ecse321.isotopecr.service.AppointmentService;
import ca.mcgill.ecse321.isotopecr.service.ProfileService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private ProfileService profileService;
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
		Vehicle vehicle = appointmentService.getVehicle(licensePlate);
		Customer customer = appointmentService.getCustomerOfVehicle(vehicle);
		Service aptService = appointmentService.getService(serviceName);
		
		Appointment appointment = appointmentService.createAppointment(customer, vehicle, technician, aptService, startTime,
				appointmentDate);
		return ControllerHelperMethods.convertToDto(appointment);
	}

	@GetMapping(value = { "/pastappointment/customer/{customer}", "/pastappointment/customer/{customer}/" })
	public List<AppointmentDto> viewPastAppointmentForCustomer(@PathVariable("customer") String email)
			throws Exception {
		try {
			Customer customer = profileService.getCustomer(email);
			List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customer);
			appointments = appointmentService.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}
			return ControllerHelperMethods.convertToDto(uncancelledappointments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@GetMapping(value = { "/futureappointment/customer/{customer}", "/futureappointment/customer/{customer}/" })
	public List<AppointmentDto> viewFutureAppointmentForCustomer(@PathVariable("customer") String email)
			throws Exception {
		try {
			Customer customer = profileService.getCustomer(email);
			List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customer);
			appointments = appointmentService.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}
			return ControllerHelperMethods.convertToDto(uncancelledappointments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@GetMapping(value = { "/pastappointment/vehicle/{vehicle}", "/pastappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewPastAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) throws Exception {
		try {
			Vehicle vehicle = appointmentService.getVehicle(licensePlate);
			Customer customer = appointmentService.getCustomerOfVehicle(vehicle);
			List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customer);
			appointments = appointmentService.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}
			return ControllerHelperMethods.convertToDto(uncancelledappointments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@GetMapping(value = { "/futureappointment/vehicle/{vehicle}", "/futureappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewFutureAppointmentForVehicle(@PathVariable("vehicle") String licensePlate) throws Exception {
		try {
			Vehicle vehicle = appointmentService.getVehicle(licensePlate);
			Customer customer = appointmentService.getCustomerOfVehicle(vehicle);
			List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customer);
			appointments = appointmentService.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED)) {
					uncancelledappointments.add(appointment);
				}
			}
			return ControllerHelperMethods.convertToDto(uncancelledappointments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "/cancelappointment/{appointment}", "/cancelappointment/{appointment}/" })
	public AppointmentDto cancelAppointment(@PathVariable("appointment") String aAppointmentId) throws Exception {
		try {
			Appointment appointment = appointmentService.getAppointmentsByID(aAppointmentId);

			appointment = appointmentService.cancelAppointment(appointment);

			return ControllerHelperMethods.convertToDto(appointment);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@PostMapping(value = { "/createInvoice/{appointment}", "/createInvoice/{appointment}/" })
	public InvoiceDto createInvoice(@PathVariable("appointment") String aAppointmentId) {
		try {
			Appointment appointment = appointmentService.getAppointmentsByID(aAppointmentId);

			Invoice invoice = appointmentService.createInvoice(appointment);

			return ControllerHelperMethods.convertToDto(invoice);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
