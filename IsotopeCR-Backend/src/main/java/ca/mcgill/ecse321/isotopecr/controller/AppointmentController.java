package ca.mcgill.ecse321.isotopecr.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import ca.mcgill.ecse321.isotopecr.dto.AppointmentDto;
import ca.mcgill.ecse321.isotopecr.dto.InvoiceDto;
import ca.mcgill.ecse321.isotopecr.dto.TimeslotDto;
import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
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

		Appointment appointment = appointmentService.createAppointment(customer, vehicle, technician, aptService,
				startTime, appointmentDate);
		return ControllerHelperMethods.convertToDto(appointment);
	}

	/**
	 * @author Victoria
	 * @param serviceName
	 * @return a list of timesloDtos
	 * @throws Exception
	 */
	@GetMapping(value = { "getUnavailableTimeslots/service", "getUnavailableTimeslots/service/" })
	public List<TimeslotDto> getUnavailableTimeslots(@PathVariable("service") String serviceName, 
			@RequestParam Integer numWeeks)
			throws Exception {
		try {
			LocalDate today = LocalDate.now().plusWeeks(numWeeks);
			if (today.getDayOfWeek()==DayOfWeek.SATURDAY || today.getDayOfWeek()==DayOfWeek.SUNDAY) {
				today.plusDays(3);
			}
			LocalDate start;
			if(numWeeks!=0) {
				start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			} else {
				start = today;
			}
  
			Service service = appointmentService.getService(serviceName);
			List<Appointment> appointments = appointmentService.getAppointmentsByService(service);
			HashMap<Timeslot, Integer> slots = new HashMap<Timeslot, Integer>();
			for (Appointment appointment : appointments) {
				for(Timeslot timeslot : appointment.getTimeslot()) {
					if(timeslot.getDate().before(java.sql.Date.valueOf(start.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))))
							&& timeslot.getDate().after(java.sql.Date.valueOf(start.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY))))) {
						break;
					}
					Integer count = slots.get(timeslot);
					if(count == null){
						slots.put(timeslot, 1);
					} else {
						slots.put(timeslot, count + 1);
					}
				}
				
			}
			List<Timeslot> timeslots = new ArrayList<Timeslot>();
			int max = service.getResource().getMaxAvailable();
			
			Iterator it = slots.entrySet().iterator();
		    while (it.hasNext()) {
		    	Map.Entry<Timeslot, Integer> element = (Map.Entry<Timeslot, Integer>)it.next();
		    	if(element.getValue()>=max || appointmentService.getFreeTechnician(element.getKey().getTime(), element.getKey().getDate())==null) {
		    		timeslots.add(element.getKey());
		    	}
		    }
			return ControllerHelperMethods.convertTimeslotsToDto(timeslots);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Jiatong
	 * @param email
	 * @return a list of past appointmentdtos related to customer find by the given
	 *         email
	 * @throws Exception
	 */
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

	/**
	 * @author Jiatong
	 * @param email
	 * @return a list of future appointmentdtos related to customer find by the
	 *         given email
	 * @throws Exception
	 */
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

	/**
	 * @author Jiatong
	 * @param licensePlate
	 * @return a list of past appointmentdtos related to vehicle find by the given
	 *         licensePlate
	 * @throws Exception
	 */
	@GetMapping(value = { "/pastappointment/vehicle/{vehicle}", "/pastappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewPastAppointmentForVehicle(@PathVariable("vehicle") String licensePlate)
			throws Exception {
		try {
			Vehicle vehicle = appointmentService.getVehicle(licensePlate);
			Customer customer = appointmentService.getCustomerOfVehicle(vehicle);
			List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customer);
			appointments = appointmentService.getAllAppointmentsBeforeTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED) && appointment.getVehicle().getLicensePlate().equals(licensePlate)) {
					uncancelledappointments.add(appointment);
				}
			}
			return ControllerHelperMethods.convertToDto(uncancelledappointments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Jiatong
	 * @param licensePlate
	 * @return a list of future appointmentdtos related to vehicle find by the given
	 *         licensePlate
	 * @throws Exception
	 */
	@GetMapping(value = { "/futureappointment/vehicle/{vehicle}", "/futureappointment/vehicle/{vehicle}/" })
	public List<AppointmentDto> viewFutureAppointmentForVehicle(@PathVariable("vehicle") String licensePlate)
			throws Exception {
		try {
			Vehicle vehicle = appointmentService.getVehicle(licensePlate);
			Customer customer = appointmentService.getCustomerOfVehicle(vehicle);
			List<Appointment> appointments = appointmentService.getAppointmentsByCustomer(customer);
			appointments = appointmentService.getAllAppointmentsAfterTime(appointments);
			List<Appointment> uncancelledappointments = new ArrayList<Appointment>();
			for (Appointment appointment : appointments) {
				if (appointment.getStatus().equals(Status.BOOKED) && appointment.getVehicle().getLicensePlate().equals(licensePlate)) {
					uncancelledappointments.add(appointment);
				}
			}
			return ControllerHelperMethods.convertToDto(uncancelledappointments);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @author Jiatong
	 * @param aAppointmentId
	 * @return the appointmentdto that is cancelled
	 * @throws Exception
	 */
	@PutMapping(value = { "/cancelappointment/{appointment}", "/cancelappointment/{appointment}/" })
	public AppointmentDto cancelAppointment(@PathVariable("appointment") String aAppointmentId) throws Exception {
		try {
			Appointment appointment = appointmentService.getAppointmentsByID(aAppointmentId);

			appointment = appointmentService.cancelAppointment(appointment);

			return ControllerHelperMethods.convertToDto(appointment);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * @author Victoria
	 * @param aAppointmentId
	 * @return The created invoice
	 * @throws Illegal Argument Exception
	 */
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
