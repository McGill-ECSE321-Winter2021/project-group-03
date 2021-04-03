package ca.mcgill.ecse321.isotopecr.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.isotopecr.dao.*;
import ca.mcgill.ecse321.isotopecr.model.*;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;

import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;

@Service
public class AppointmentService {
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	TechnicianRepository technicianRepository;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	VehicleRepository vehicleRepository;
	@Autowired
	CompanyProfileRepository companyProfileRepository;
	@Autowired
	AutoRepairShopRepository autoRepairShopRepository;
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	ResourceRepository resourceRepository;
	@Autowired
	TimeslotRepository timeslotRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	DailyAvailabilityRepository dailyAvailabilityRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	/**
	 * @author Zichen
	 * @param time
	 * @return a technician available at that time
	 */
	@Transactional
	public Technician getFreeTechnician(Time time, Date date) {
		java.util.Date utilDate = new java.util.Date(date.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(utilDate);
		int dayOfWeeki = c.get(Calendar.DAY_OF_WEEK);
		DayOfWeek dayOfWeek = intToDayOfWeek(dayOfWeeki);

		// look for the first available technician in the system
		for (Technician technician : technicianRepository.findAll()) {
			for (DailyAvailability availability : technician.getDailyAvailability()) {
				if (availability.getDay().equals(dayOfWeek)) {
					LocalTime request = time.toLocalTime();
					LocalTime start = availability.getStartTime().toLocalTime();
					LocalTime end = availability.getEndTime().toLocalTime();

					if (start.isBefore(request) && end.isAfter(request)) {
						return technician;
					}
				}
			}
		}
		return null;
	}

	@Transactional
	public Vehicle getVehicle(String licensePlate) {
		if (licensePlate == null) {
			throw new IllegalArgumentException("ERROR: the vehicle licensePlate is null.");
		}
		Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate);
		if (vehicle == null) {
			throw new IllegalArgumentException("ERROR: the vehicle cannot be found.");
		}
		return vehicle;
	}

	@Transactional
	public Customer getCustomerOfVehicle(Vehicle vehicle) {
		if (vehicle == null) {
			throw new IllegalArgumentException("ERROR: the vehicle is null.");
		}
		Customer customer = customerRepository.findCustomerByVehicle(vehicle);
		if (customer == null) {
			throw new IllegalArgumentException("ERROR: the customer cannot be found.");
		}
		return customer;
	}

	@Transactional
	public ca.mcgill.ecse321.isotopecr.model.Service getService(String serviceName) {
		if (serviceName == null) {
			throw new IllegalArgumentException("ERROR: the service name is null.");
		}
		ca.mcgill.ecse321.isotopecr.model.Service service = serviceRepository.findServiceByServiceName(serviceName);
		if (service == null) {
			throw new IllegalArgumentException("ERROR: the service cannot be found.");
		}
		return service;
	}

	/**
	 * @author Jiatong
	 * @param customer
	 * @param vehicle
	 * @param technician
	 * @param service
	 * @param startTime
	 * @param chosenDate
	 * @return an appointment that is just created
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public Appointment createAppointment(Customer customer, Vehicle vehicle, Technician technician,
			ca.mcgill.ecse321.isotopecr.model.Service service, Time startTime, Date chosenDate)
			throws IllegalArgumentException {

		if (!ServiceHelperMethods.isValidCustomer(customer) || !ServiceHelperMethods.isValidVehicle(vehicle)
				|| !ServiceHelperMethods.isValidTechnician(technician)
				|| !ServiceHelperMethods.isValidService(service)) {
			throw new IllegalArgumentException("Invalid input for booking an appointment.");
		}

		Appointment appointment = new Appointment();
		Integer duration = service.getDuration();
		Integer timeslotnum = (int) Math.ceil(duration / 30.0);
		Set<Timeslot> timeslots = new HashSet<Timeslot>();

		for (int i = 0; i < timeslotnum; i++) {
			Timeslot ts = new Timeslot();
			ts.setDate(chosenDate);
			ts.setTime(startTime);
			ts.setSlotID(String.valueOf(chosenDate) + String.valueOf(startTime));
			if (timeslotRepository.findTimeslotBySlotID(ts.getSlotID()) == null) {
				timeslotRepository.save(ts);
				timeslots.add(ts);
			} else {
				Timeslot existTimeslot = timeslotRepository.findTimeslotBySlotID(ts.getSlotID());
				timeslots.add(existTimeslot);
			}

			LocalTime localtime = startTime.toLocalTime();
			localtime = localtime.plusMinutes(30);
			startTime = Time.valueOf(localtime);
		}

		Timeslot timeslot = timeslots.iterator().next();
		appointment.setAppointmentID(String.valueOf(customer.getProfileID().hashCode()
				* vehicle.getLicensePlate().hashCode() * timeslot.getSlotID().hashCode()));
		appointment.setCustomer(customer);
		appointment.setVehicle(vehicle);
		appointment.setTechnician(technician);
		appointment.setService(service);
		appointment.setTimeslot(timeslots);
		appointment.setStatus(Status.BOOKED);

		appointmentRepository.save(appointment);

		for (Timeslot t : timeslots) {
			Set<Appointment> appointments = new HashSet<Appointment>();
			if (t.getAppointment() != null) {
				appointments = t.getAppointment();
			}
			appointments.add(appointment);
			t.setAppointment(appointments);
			timeslotRepository.save(t);
		}

		return appointment;
	}

	/**
	 * @author Jiatong
	 * @param appointment
	 * @return appointment that is cancelled
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public Appointment cancelAppointment(Appointment appointment) throws IllegalArgumentException {

		if (appointment == null) {
			throw new IllegalArgumentException("There is no such appointment in the system");
		}
		String appointmentID = appointment.getAppointmentID();

		Appointment aptmt = appointmentRepository.findAppointmentByAppointmentID(appointmentID);
		Set<Timeslot> timeslots = aptmt.getTimeslot();
		Date aDate = timeslots.iterator().next().getDate();
		if (ServiceHelperMethods.isBeforeADay(aDate)) {
			aptmt.setStatus(Status.CANCELED);

			appointmentRepository.save(appointment);

		} else {
			throw new IllegalArgumentException("Sorry, you are not able to cancle the appointment within 24 hours");
		}
		return appointment;

	}

	/**
	 * @author Victoria
	 * @param appointment
	 * @return invoice that is just created
	 */
	@Transactional
	public Invoice createInvoice(Appointment appointment) {
		Invoice invoice = new Invoice();
		invoice.setCost(appointment.getService().getPrice());
		invoice.setInvoiceID(
				String.valueOf(appointment.getService().getPrice() * appointment.getAppointmentID().hashCode()));
		invoice.setIsPaid(true);
		appointment.setInvoice(invoice);
		appointmentRepository.save(appointment);
		invoiceRepository.save(invoice);
		return invoice;
	}

	/**
	 * @author Jiatong
	 * @return A list of all appointments that is in the system
	 */
	@Transactional
	public List<Appointment> getAllAppointments() {
		return ServiceHelperMethods.toList(appointmentRepository.findAll());
	}

	/**
	 * @author Jiatong
	 * @param appointment: a list of appointments that we want to go through
	 * @return A list of appointment that is before current time (past appointments)
	 */
	@Transactional
	public List<Appointment> getAllAppointmentsBeforeTime(List<Appointment> appointments)
			throws IllegalArgumentException {
		if (appointments.isEmpty()) {
			throw new IllegalArgumentException("There is no appointments in the past.");
		}
		Date curDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		Time curTime = new java.sql.Time(Calendar.getInstance().getTimeInMillis());
		List<Appointment> aptmtBeforeTime = new ArrayList<Appointment>();
		for (Appointment aptmt : appointments) {
			boolean isBefore = true;

			Set<Timeslot> timeslots = aptmt.getTimeslot();
			Timeslot timeslot = timeslots.iterator().next();

			if (timeslot.getDate().after(curDate)
					|| (timeslot.getDate().equals(curDate) && timeslot.getTime().after(curTime))) {
				isBefore = false;
			}

			if (isBefore == true)
				aptmtBeforeTime.add(aptmt);
		}

		return aptmtBeforeTime;
	}

	/**
	 * @author Jiatong
	 * @param appointment: a list of appointments that we want to go through
	 * @return A list of appointment that is after current time (future
	 *         appointments)
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public List<Appointment> getAllAppointmentsAfterTime(List<Appointment> appointments)
			throws IllegalArgumentException {
		if (appointments.isEmpty()) {
			throw new IllegalArgumentException("There is no appointments in the future.");
		}
		Date curDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		Time curTime = new java.sql.Time(Calendar.getInstance().getTimeInMillis());
		List<Appointment> aptmtBeforeTime = new ArrayList<Appointment>();
		for (Appointment aptmt : appointments) {
			boolean isBefore = true;

			Set<Timeslot> timeslots = aptmt.getTimeslot();
			Timeslot timeslot = timeslots.iterator().next();

			if (timeslot.getDate().after(curDate)
					|| (timeslot.getDate().equals(curDate) && timeslot.getTime().after(curTime))) {
				isBefore = false;
			}
			if (isBefore == false)
				aptmtBeforeTime.add(aptmt);
		}
		return aptmtBeforeTime;
	}

	/**
	 * @author Jiatong
	 * @param aCustomer
	 * @return A list of appointment that the customer of the appointment is the
	 *         input customer
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public List<Appointment> getAppointmentsByCustomer(Customer aCustomer) throws IllegalArgumentException {
		if (ServiceHelperMethods.isValidCustomer(aCustomer)) {
			List<Appointment> allAppointmentByPerson = appointmentRepository.findAppointmentByCustomer(aCustomer);
			return allAppointmentByPerson;
		} else {
			throw new IllegalArgumentException("Invalid customer");
		}
	}

	/**
	 * @author Jiatong
	 * @param technician
	 * @return A list of appointment that the technician of the appointment is the
	 *         input technician
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public List<Appointment> getAppointmentsByTechnician(Technician technician) throws IllegalArgumentException {
		if (ServiceHelperMethods.isValidTechnician(technician)) {
			List<Appointment> allAppointmentByTechnician = appointmentRepository
					.findAppointmentByTechnician(technician);
			return allAppointmentByTechnician;
		} else {
			throw new IllegalArgumentException("Invalid technician");
		}
	}

	/**
	 * @author Jiatong
	 * @param vehicle
	 * @return A list of appointment that the vehicle of the appointment is the
	 *         input vehicle
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public List<Appointment> getAppointmentsByVehicle(Vehicle vehicle) throws IllegalArgumentException {
		if (ServiceHelperMethods.isValidVehicle(vehicle)) {
			List<Appointment> allAppointmentByVehicle = appointmentRepository.findAppointmentByVehicle(vehicle);
			return allAppointmentByVehicle;
		} else {
			throw new IllegalArgumentException("Invalid vehicle.");
		}
	}

	/**
	 * @author Jiatong
	 * @param service
	 * @return A list of appointment that the service technician of the appointment
	 *         is the input service
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public List<Appointment> getAppointmentsByService(ca.mcgill.ecse321.isotopecr.model.Service service)
			throws IllegalArgumentException {
		if (ServiceHelperMethods.isValidService(service)) {
			List<Appointment> allAppointmentByService = appointmentRepository
					.findAppointmentByService((ca.mcgill.ecse321.isotopecr.model.Service) service);
			return allAppointmentByService;
		} else {
			throw new IllegalArgumentException("Invalid service");
		}
	}

	/**
	 * @author Jiatong
	 * @param id
	 * @return An appointment that the id of the appointment is the input id
	 * @throws IllegalArgumentException
	 */
	@Transactional
	public Appointment getAppointmentsByID(String id) throws IllegalArgumentException {
		if (id != null) {
			Appointment appointment = appointmentRepository.findAppointmentByAppointmentID(id);
			return appointment;
		} else {
			throw new IllegalArgumentException("Invalid appointment id");
		}
	}

	/**
	 * Helper method
	 * 
	 * @author Zichen
	 * @param dayOfWeeki
	 * @return
	 */
	private DayOfWeek intToDayOfWeek(int dayOfWeeki) {
		switch (dayOfWeeki) {

		case 2:

			return DayOfWeek.Monday;
		case 3:
			return DayOfWeek.Tuesday;
		case 4:
			return DayOfWeek.Wednesday;
		case 5:
			return DayOfWeek.Thursday;
		case 6:
			return DayOfWeek.Friday;

		}
		return null;
	}
}
