package ca.mcgill.ecse321.isotopecr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.isotopecr.dao.AppointmentRepository;
import ca.mcgill.ecse321.isotopecr.dao.CustomerRepository;
import ca.mcgill.ecse321.isotopecr.dao.InvoiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.ServiceRepository;
import ca.mcgill.ecse321.isotopecr.dao.TechnicianRepository;
import ca.mcgill.ecse321.isotopecr.dao.TimeslotRepository;
import ca.mcgill.ecse321.isotopecr.dao.VehicleRepository;
import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.Appointment.Status;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability.DayOfWeek;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;;

@ExtendWith(MockitoExtension.class)
public class TestAppointmentService {
	@Mock
	private AppointmentRepository appointmentRepository;
	@Mock
	private TechnicianRepository technicianRepository;
	@Mock
	private TimeslotRepository timeslotRepository;
	@Mock
	private VehicleRepository vehicleRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ServiceRepository serviceRepository;
	@Mock
	private InvoiceRepository invoiceRepository;

	@InjectMocks
	private AppointmentService appointmentService;

	private static final boolean ISREGISTERED = true;

	/* Mock up Technician */
	private static final String TECH_ID = "tech1";
	private static final String TECH_EMAIL = "tech1@isotopecr.com";
	private static final String TECH_FIRSTNAME = "James";
	private static final String TECH_LASTNAME = "Lan";
	private static final String TECH_PASSWORD = "Tech_password2";
	/* Mock up Customer */
	private static final String CUST_ID = "cust1";
	private static final String CUST_EMAIL = "cust1@isotopecr.com";
	private static final String CUST_FIRSTNAME = "MARWAN";
	private static final String CUST_LASTNAME = "KANAAN";
	private static final String CUST_PASSWORD = "Cust_password3";
	private static final String CUST_PHONE = "4831235543";

	/* Mock up Services */
	private static final String SERVICE1 = "CarWash";
	private static final double PRICE1 = 23.33;
	private static final int FREQUENCY1 = 2;
	private static final Integer DURATION1 = 5;
	private static final String SERVICE2 = "TireChange";
	private static final double PRICE2 = 35.2;
	private static final int FREQUENCY2 = 1;
	private static final Integer DURATION2 = 2;

	/* Mock up Resources */
	private static final String RESOURCE_TYPE1 = "Pull Car";
	private static final Integer MAX1 = 5;
	private static final String RESOURCE_TYPE2 = "Scaffold";
	private static final Integer MAX2 = 2;

	/* Mock up Vehicles */
	private static final String LICENSEPLATE1 = "xxxx1";
	private static final String LICENSEPLATE2 = "xxxx2";
	private static final String BRAND1 = "Nissan";
	private static final String BRAND2 = "Volkswagen";
	private static final String MODEL1 = "GT-R";
	private static final String MODEL2 = "Tiguan";
	private static final String YEAR1 = "2016";
	private static final String YEAR2 = "2018";

	/* Mock up Invoice */
	private static final String INVOICE_ID = "12345";
	private static final double COST = 150;
	private static final boolean ISPAID = true;

	/* Mock up Timeslot */
	/* Before */
	private static final Date DATE = java.sql.Date.valueOf(LocalDate.of(2021, Month.MARCH, 15));
	private static final Time TIME = java.sql.Time.valueOf(LocalTime.of(11, 35));
	private static final String SLOT_ID = "slotID";
	/* Future */
	private static final Date DATE1 = java.sql.Date.valueOf(LocalDate.of(2033, Month.MARCH, 15));
	private static final Time TIME1 = java.sql.Time.valueOf(LocalTime.of(11, 35));
	private static final String SLOT_ID1 = "slotID1";

	/* Mock up Appointment */
	private static final String APPOINTMENT_ID1 = "appointment1";
	private static final String APPOINTMENT_ID2 = "appointment2";
	private static final Status STATUS = Status.BOOKED;

	private static final java.sql.Time STARTTIME = java.sql.Time.valueOf(LocalTime.of(15, 00));
	private static final java.sql.Date CHOSENDATE = java.sql.Date.valueOf(LocalDate.of(2021, Month.MARCH, 15));
	private static final String SLOTID = String.valueOf(CHOSENDATE) + String.valueOf(STARTTIME);

	private final Customer CUSTOMER = new Customer();
	private final Vehicle VEHICLE = new Vehicle();
	private final Technician TECHNICIAN = new Technician();
	private final Service SERVICE = new Service();

	private Appointment APPOINTMENT = new Appointment();

	@BeforeEach
	public void setMockOutput() {
		/* ---------------------------- AppointmentRepo -------------------------- */
		/**
		 * Mock behavior for findAdminByProfileID
		 */
		lenient().when(appointmentRepository.findAppointmentByAppointmentID(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(APPOINTMENT_ID1)) {
						Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
						Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

						Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
						Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
						Set<Service> services = new HashSet<Service>();
						services.add(service1);
						services.add(service2);

						Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
						Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(vehicle1);
						vehicles.add(vehicle2);

						Invoice invoice = createInvoice(INVOICE_ID, COST, ISPAID);

						Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

						Timeslot slot = createTimeslot(DATE, TIME, SLOT_ID);
						Set<Timeslot> slots = new HashSet<Timeslot>();
						slots.add(slot);

						Technician tech = mockTechnician(dailyAvailabilities, services);

						Customer customer = mockCustomer(vehicles);

						Appointment appointment = new Appointment();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(appointment);
						slot.setAppointment(appointments);

						appointment.setAppointmentID(APPOINTMENT_ID1);
						appointment.setStatus(STATUS);
						appointment.setInvoice(invoice);
						appointment.setService(service1);
						appointment.setTechnician(tech);
						appointment.setCustomer(customer);
						appointment.setTimeslot(slots);
						appointment.setVehicle(vehicle1);
						return appointment;
					} else if (invocation.getArgument(0).equals(APPOINTMENT_ID2)) {
						Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
						Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

						Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
						Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
						Set<Service> services = new HashSet<Service>();
						services.add(service1);
						services.add(service2);

						Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
						Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(vehicle1);
						vehicles.add(vehicle2);

						Invoice invoice = createInvoice(INVOICE_ID, COST, ISPAID);

						Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

						Timeslot slot = createTimeslot(DATE1, TIME1, SLOT_ID1);
						Set<Timeslot> slots = new HashSet<Timeslot>();
						slots.add(slot);

						Technician tech = mockTechnician(dailyAvailabilities, services);

						Customer customer = mockCustomer(vehicles);

						Appointment appointment = new Appointment();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(appointment);
						slot.setAppointment(appointments);

						appointment.setAppointmentID(APPOINTMENT_ID1);
						appointment.setStatus(STATUS);
						appointment.setInvoice(invoice);
						appointment.setService(service1);
						appointment.setTechnician(tech);
						appointment.setCustomer(customer);
						appointment.setTimeslot(slots);
						appointment.setVehicle(vehicle1);
						return appointment;

					} else {
						return null;
					}
				});

		lenient().when(appointmentRepository.findAppointmentByCustomer(any(Customer.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(CUSTOMER)) {
						Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
						Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

						Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
						Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
						Set<Service> services = new HashSet<Service>();
						services.add(service1);
						services.add(service2);

						Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
						Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(vehicle1);
						vehicles.add(vehicle2);

						Invoice invoice = createInvoice(INVOICE_ID, COST, ISPAID);

						Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

						Timeslot slot = createTimeslot(DATE, TIME, SLOT_ID);
						Set<Timeslot> slots = new HashSet<Timeslot>();
						slots.add(slot);

						Technician tech = mockTechnician(dailyAvailabilities, services);

						Customer customer = CUSTOMER;

						Appointment appointment = new Appointment();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(appointment);
						slot.setAppointment(appointments);

						appointment.setAppointmentID(APPOINTMENT_ID1);
						appointment.setStatus(STATUS);
						appointment.setInvoice(invoice);
						appointment.setService(service1);
						appointment.setTechnician(tech);
						appointment.setCustomer(customer);
						appointment.setTimeslot(slots);
						appointment.setVehicle(vehicle1);

						return toList(appointments);
					} else {
						return null;
					}
				});

		lenient().when(appointmentRepository.findAppointmentByVehicle(any(Vehicle.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(VEHICLE)) {
						Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
						Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

						Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
						Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
						Set<Service> services = new HashSet<Service>();
						services.add(service1);
						services.add(service2);

						Vehicle vehicle1 = VEHICLE;
						Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(vehicle1);
						vehicles.add(vehicle2);

						Invoice invoice = createInvoice(INVOICE_ID, COST, ISPAID);

						Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

						Timeslot slot = createTimeslot(DATE, TIME, SLOT_ID);
						Set<Timeslot> slots = new HashSet<Timeslot>();
						slots.add(slot);

						Technician tech = mockTechnician(dailyAvailabilities, services);

						Customer customer = mockCustomer(vehicles);

						Appointment appointment = new Appointment();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(appointment);
						slot.setAppointment(appointments);

						appointment.setAppointmentID(APPOINTMENT_ID1);
						appointment.setStatus(STATUS);
						appointment.setInvoice(invoice);
						appointment.setService(service1);
						appointment.setTechnician(tech);
						appointment.setCustomer(customer);
						appointment.setTimeslot(slots);
						appointment.setVehicle(vehicle1);

						return toList(appointments);
					} else {
						return null;
					}
				});

		lenient().when(appointmentRepository.findAppointmentByTechnician(any(Technician.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(TECHNICIAN)) {
						Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
						Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

						Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
						Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
						Set<Service> services = new HashSet<Service>();
						services.add(service1);
						services.add(service2);

						Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
						Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(vehicle1);
						vehicles.add(vehicle2);

						Invoice invoice = createInvoice(INVOICE_ID, COST, ISPAID);

						Timeslot slot = createTimeslot(DATE, TIME, SLOT_ID);
						Set<Timeslot> slots = new HashSet<Timeslot>();
						slots.add(slot);

						Technician tech = TECHNICIAN;

						Customer customer = mockCustomer(vehicles);

						Appointment appointment = new Appointment();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(appointment);
						slot.setAppointment(appointments);

						appointment.setAppointmentID(APPOINTMENT_ID1);
						appointment.setStatus(STATUS);
						appointment.setInvoice(invoice);
						appointment.setService(service1);
						appointment.setTechnician(tech);
						appointment.setCustomer(customer);
						appointment.setTimeslot(slots);
						appointment.setVehicle(vehicle1);

						return toList(appointments);
					} else {
						return null;
					}
				});

		lenient().when(appointmentRepository.findAppointmentByService(any(Service.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(SERVICE)) {

						Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

						Service service1 = SERVICE;
						Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
						Set<Service> services = new HashSet<Service>();
						services.add(service1);
						services.add(service2);

						Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
						Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(vehicle1);
						vehicles.add(vehicle2);

						Invoice invoice = createInvoice(INVOICE_ID, COST, ISPAID);

						Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

						Timeslot slot = createTimeslot(DATE, TIME, SLOT_ID);
						Set<Timeslot> slots = new HashSet<Timeslot>();
						slots.add(slot);

						Technician tech = mockTechnician(dailyAvailabilities, services);

						Customer customer = mockCustomer(vehicles);

						Appointment appointment = new Appointment();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(appointment);
						slot.setAppointment(appointments);

						appointment.setAppointmentID(APPOINTMENT_ID1);
						appointment.setStatus(STATUS);
						appointment.setInvoice(invoice);
						appointment.setService(service1);
						appointment.setTechnician(tech);
						appointment.setCustomer(customer);
						appointment.setTimeslot(slots);
						appointment.setVehicle(vehicle1);
						return toList(appointments);
					} else {
						return null;
					}
				});

		lenient().when(technicianRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
			Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

			Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
			Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
			Set<Service> services = new HashSet<Service>();
			services.add(service1);
			services.add(service2);

			Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

			Technician tech = mockTechnician(dailyAvailabilities, services);
			List<Technician> technicianList = new ArrayList<>();
			technicianList.add(tech);
			return technicianList;
		});

		lenient().when(appointmentRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Appointment appointment1 = new Appointment();
			appointment1.setAppointmentID(APPOINTMENT_ID1);

			Appointment appointment2 = new Appointment();
			appointment2.setAppointmentID(APPOINTMENT_ID2);

			Timeslot slot = createTimeslot(DATE, TIME, SLOT_ID);
			Set<Timeslot> slots = new HashSet<Timeslot>();
			slots.add(slot);
			appointment1.setTimeslot(slots);

			Timeslot slotFuture = createTimeslot(DATE1, TIME1, SLOT_ID1);
			Set<Timeslot> slotsFuture = new HashSet<Timeslot>();
			slotsFuture.add(slotFuture);
			appointment2.setTimeslot(slotsFuture);

			List<Appointment> appointmentList = new ArrayList<Appointment>();
			appointmentList.add(appointment1);
			appointmentList.add(appointment2);
			return appointmentList;
		});

		lenient().when(timeslotRepository.findTimeslotBySlotID(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(SLOTID)) {
						Timeslot timeslot = new Timeslot();
						Set<Appointment> appointments = new HashSet<Appointment>();
						appointments.add(APPOINTMENT);

						timeslot.setAppointment(appointments);
						timeslot.setTime(STARTTIME);
						timeslot.setDate(CHOSENDATE);
						timeslot.setSlotID(SLOTID);

						return timeslot;

					} else {
						return null;
					}
				});

		lenient().when(vehicleRepository.findVehicleByLicensePlate(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(LICENSEPLATE1)) {
						Vehicle vehicle = new Vehicle();
						vehicle.setLicensePlate(LICENSEPLATE1);
						return vehicle;
					} else {
						return null;
					}
				});

		lenient().when(serviceRepository.findServiceByServiceName(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(SERVICE1)) {
						Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
						Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);

						return service1;
					} else {
						return null;
					}
				});

		lenient().when(customerRepository.findCustomerByVehicle(any(Vehicle.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(VEHICLE)) {
						Set<Vehicle> vehicles = new HashSet<Vehicle>();
						vehicles.add(VEHICLE);

						Customer customer = mockCustomer(vehicles);
						return customer;
					} else {
						return null;
					}
				});

		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(appointmentRepository.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(timeslotRepository.save(any(Timeslot.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(invoiceRepository.save(any(Invoice.class))).thenAnswer(returnParameterAsAnswer);
	}

	/**
	 * Test if getfreetechnician in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void testGetFreeTechnician() {
		// Monday normal working hours
		Time time = Time.valueOf(LocalTime.of(11, 35));
		Date date = Date.valueOf(LocalDate.of(2021, Month.MARCH, 15));

		Technician tech = appointmentService.getFreeTechnician(time, date);

		assertEquals(TECH_ID, tech.getProfileID());
	}

	/**
	 * Test getFreeTechnician when a offday is passed
	 * 
	 * @author Zichen
	 */
	@Test
	public void testGetFreeTechnicianOnOffDay() {
		// that's a Sunday
		Time time = Time.valueOf(LocalTime.of(11, 35));
		Date date = Date.valueOf(LocalDate.of(2021, Month.MARCH, 14));

		Technician tech = appointmentService.getFreeTechnician(time, date);

		assertEquals(null, tech);
	}

	/**
	 * Test BookAppointment in normal use case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void testBookAppointment() {
		Appointment appointment = null;

		Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
		Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

		Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
		Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
		Set<Service> services = new HashSet<Service>();
		services.add(service1);
		services.add(service2);

		Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

		Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
		Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle1);
		vehicles.add(vehicle2);

		Customer customer = mockCustomer(vehicles);

		Technician technician = mockTechnician(dailyAvailabilities, services);
		try {
			appointment = appointmentService.createAppointment(customer, vehicle1, technician, service1, STARTTIME,
					CHOSENDATE);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointment);
		// check the appointment is the correct one just created
		assertEquals(customer.getProfileID(), appointment.getCustomer().getProfileID());
		assertEquals(technician.getProfileID(), appointment.getTechnician().getProfileID());
		assertEquals(vehicle1.getLicensePlate(), appointment.getVehicle().getLicensePlate());
		assertEquals(service1.getServiceName(), appointment.getService().getServiceName());
		assertEquals(true, appointment.getTimeslot().iterator().hasNext());
		Timeslot timeSlot = appointment.getTimeslot().iterator().next();
		assertEquals(STARTTIME, timeSlot.getTime());
		assertEquals(CHOSENDATE, timeSlot.getDate());
	}

	/**
	 * Test BookAppointment when input customer has problem.
	 * 
	 * @author Zichen
	 */
	@Test
	public void testBookAppointmentCustomerNull() {
		Appointment appointment = null;
		String error = null;

		Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
		Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

		Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
		Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
		Set<Service> services = new HashSet<Service>();
		services.add(service1);
		services.add(service2);

		Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

		Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
		Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle1);
		vehicles.add(vehicle2);

		Technician technician = mockTechnician(dailyAvailabilities, services);
		try {
			appointment = appointmentService.createAppointment(null, vehicle1, technician, service1, STARTTIME,
					CHOSENDATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(appointment);
		assertNotNull(error);
		assertEquals("Invalid input for booking an appointment.", error);
	}

	/**
	 * Test BookAppointment when input vehicle has problem.
	 * 
	 * @author Zichen
	 */
	@Test
	public void testBookAppointmentVehicleNull() {
		Appointment appointment = null;
		String error = null;

		Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
		Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

		Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
		Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
		Set<Service> services = new HashSet<Service>();
		services.add(service1);
		services.add(service2);

		Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

		Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
		Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle1);
		vehicles.add(vehicle2);

		Customer customer = mockCustomer(vehicles);

		Technician technician = mockTechnician(dailyAvailabilities, services);
		try {
			appointment = appointmentService.createAppointment(customer, null, technician, service1, STARTTIME,
					CHOSENDATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(appointment);
		assertNotNull(error);
		assertEquals("Invalid input for booking an appointment.", error);
	}

	/**
	 * Test BookAppointment when input technician has problem.
	 * 
	 * @author Zichen
	 */
	@Test
	public void testBookAppointmentTechnicianNull() {
		Appointment appointment = null;
		String error = null;

		Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
		Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

		Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
		Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
		Set<Service> services = new HashSet<Service>();
		services.add(service1);
		services.add(service2);

		Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
		Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle1);
		vehicles.add(vehicle2);

		Customer customer = mockCustomer(vehicles);

		try {
			appointment = appointmentService.createAppointment(customer, vehicle1, null, service1, STARTTIME,
					CHOSENDATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(appointment);
		assertNotNull(error);
		assertEquals("Invalid input for booking an appointment.", error);
	}

	/**
	 * Test BookAppointment when input service has problem.
	 * 
	 * @author Zichen
	 */
	@Test
	public void testBookAppointmentServiceNull() {
		Appointment appointment = null;
		String error = null;

		Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
		Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);

		Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
		Service service2 = createService(SERVICE2, resource2, PRICE2, FREQUENCY2, DURATION2);
		Set<Service> services = new HashSet<Service>();
		services.add(service1);
		services.add(service2);

		Set<DailyAvailability> dailyAvailabilities = createSetAvailabilities();

		Vehicle vehicle1 = createVehicle(LICENSEPLATE1, BRAND1, MODEL1, YEAR1);
		Vehicle vehicle2 = createVehicle(LICENSEPLATE2, BRAND2, MODEL2, YEAR2);
		Set<Vehicle> vehicles = new HashSet<Vehicle>();
		vehicles.add(vehicle1);
		vehicles.add(vehicle2);

		Customer customer = mockCustomer(vehicles);

		Technician technician = mockTechnician(dailyAvailabilities, services);
		try {
			appointment = appointmentService.createAppointment(customer, vehicle1, technician, null, STARTTIME,
					CHOSENDATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(appointment);
		assertNotNull(error);
		assertEquals("Invalid input for booking an appointment.", error);
	}

	/**
	 * Test CancelAppointment in normal use case(the appointment is at least 24hrs
	 * after now);
	 * 
	 * @author Zichen
	 */
	@Test
	public void testCancelAppointment() {
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(APPOINTMENT_ID2);

		Appointment canceledAppointment = null;
		try {
			canceledAppointment = appointmentService.cancelAppointment(appointment);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(canceledAppointment);
	}

	/**
	 * Test CancelAppointment when input is null;
	 * 
	 * @author Zichen
	 */
	@Test
	public void testCancelAppointmentNull() {
		Appointment appointment = null;
		String error = null;

		try {
			appointment = appointmentService.cancelAppointment(appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(appointment);
		assertNotNull(error);
		assertEquals("There is no such appointment in the system", error);
	}

	/**
	 * Test CancelAppointment when the appointment is already 24 hrs away;
	 * 
	 * @author Zichen
	 */
	@Test
	public void testCancelAppointmentWithIn24Hrs() {
		String error = null;
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(APPOINTMENT_ID1); // appointment_ID1 is the appointment passed

		Appointment canceledAppointment = null;
		try {
			canceledAppointment = appointmentService.cancelAppointment(appointment);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
			System.out.println(error);
		}

		assertNull(canceledAppointment);
		assertEquals("Sorry, you are not able to cancle the appointment within 24 hours", error);
	}

	/**
	 * Test getAllAppointments.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAllAppointments() {
		List<Appointment> appointments = appointmentService.getAllAppointments();

		assertNotNull(appointments);
		assertEquals(2, appointments.size());
		assertEquals(APPOINTMENT_ID1, appointments.get(0).getAppointmentID());
		assertEquals(APPOINTMENT_ID2, appointments.get(1).getAppointmentID());
	}

	/**
	 * Test getAllAppointmentsBeforeTime in normal case. Inside all appointments,
	 * one appointment is before and one is in future
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAllAppointmentsBeforeTime() {
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
		List<Appointment> appointmentsBefore = null;
		try {
			appointmentsBefore = appointmentService.getAllAppointmentsBeforeTime(appointments);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointmentsBefore);
		assertEquals(1, appointmentsBefore.size());
		assertEquals(APPOINTMENT_ID1, appointmentsBefore.get(0).getAppointmentID());
	}

	/**
	 * Test getAllAppointmentsBeforeTime when there're no appointments yet in db.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAllAppointmentsBeforeTimeNoAppointments() {
		List<Appointment> appointments = new ArrayList<Appointment>();
		List<Appointment> appointmentsBefore = null;
		String error = null;
		try {
			appointmentsBefore = appointmentService.getAllAppointmentsBeforeTime(appointments);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(appointmentsBefore);
		assertEquals("There is no appointments in the past.", error);
	}

	/**
	 * Test getAllAppointmentsAfterTime in normal case. Inside all appointments, one
	 * appointment is before and one is in future
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAllAppointmentsAfterTime() {
		List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
		List<Appointment> appointmentsAfter = null;
		try {
			appointmentsAfter = appointmentService.getAllAppointmentsAfterTime(appointments);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointmentsAfter);
		assertEquals(1, appointmentsAfter.size());
		assertEquals(APPOINTMENT_ID2, appointmentsAfter.get(0).getAppointmentID());
	}

	/**
	 * Test getAllAppointmentsAfterTime when there're no appointments yet in db.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAllAppointmentsAfterTimeNoAppointments() {
		List<Appointment> appointments = new ArrayList<Appointment>();
		List<Appointment> appointmentsAfter = null;
		String error = null;
		try {
			appointmentsAfter = appointmentService.getAllAppointmentsAfterTime(appointments);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(appointmentsAfter);
		assertEquals("There is no appointments in the future.", error);
	}

	/**
	 * Test getAppointmentsByCustomer in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByCustomer() {
		List<Appointment> appointmentsByCustomer = null;
		try {
			appointmentsByCustomer = appointmentService.getAppointmentsByCustomer(CUSTOMER);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointmentsByCustomer);
		// the returned appointment should contain CUSTOMER
		assertEquals(CUSTOMER, appointmentsByCustomer.get(0).getCustomer());
		// the returned appointment should have id = APPOINTMENT_ID1
		assertEquals(APPOINTMENT_ID1, appointmentsByCustomer.get(0).getAppointmentID());
	}

	/**
	 * Test getAppointmentsByCustomer when input customer does not exist.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByCustomerNull() {
		List<Appointment> appointmentsByCustomer = null;
		String error = null;
		try {
			appointmentsByCustomer = appointmentService.getAppointmentsByCustomer(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(appointmentsByCustomer);
		assertEquals("Invalid customer", error);
	}

	/**
	 * Test getAppointmentsByTechnician in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByTechnician() {
		List<Appointment> appointmentsByTechnician = null;
		try {
			appointmentsByTechnician = appointmentService.getAppointmentsByTechnician(TECHNICIAN);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointmentsByTechnician);
		// the returned appointment should contain CUSTOMER
		assertEquals(TECHNICIAN, appointmentsByTechnician.get(0).getTechnician());
		// the returned appointment should have id = APPOINTMENT_ID1
		assertEquals(APPOINTMENT_ID1, appointmentsByTechnician.get(0).getAppointmentID());
	}

	/**
	 * Test getAppointmentsByTechnician when input technician does not exist.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByTechnicianNull() {
		List<Appointment> appointmentsByTechnician = null;
		String error = null;
		try {
			appointmentsByTechnician = appointmentService.getAppointmentsByTechnician(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(appointmentsByTechnician);
		assertEquals("Invalid technician", error);
	}

	/**
	 * Test getAppointmentsByVehicle in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByVehicle() {
		List<Appointment> appointmentsByVehicle = null;
		try {
			appointmentsByVehicle = appointmentService.getAppointmentsByVehicle(VEHICLE);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointmentsByVehicle);
		// the returned appointment should contain CUSTOMER
		assertEquals(VEHICLE, appointmentsByVehicle.get(0).getVehicle());
		// the returned appointment should have id = APPOINTMENT_ID1
		assertEquals(APPOINTMENT_ID1, appointmentsByVehicle.get(0).getAppointmentID());
	}

	/**
	 * Test getAppointmentsByVehicle when input vehicle does not exist.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByVehicleNull() {
		List<Appointment> appointmentsByVehicle = null;
		String error = null;
		try {
			appointmentsByVehicle = appointmentService.getAppointmentsByVehicle(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(appointmentsByVehicle);
		assertEquals("Invalid vehicle.", error);
	}

	/**
	 * Test getAppointmentsByService in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByService() {
		List<Appointment> appointmentsByService = null;
		try {
			appointmentsByService = appointmentService.getAppointmentsByService(SERVICE);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointmentsByService);
		// the returned appointment should contain CUSTOMER
		assertEquals(SERVICE, appointmentsByService.get(0).getService());
		// the returned appointment should have id = APPOINTMENT_ID1
		assertEquals(APPOINTMENT_ID1, appointmentsByService.get(0).getAppointmentID());
	}

	/**
	 * Test getAppointmentsByService when input service does not exist.
	 * 
	 * @author Zichen
	 */
	@Test
	public void getAppointmentsByServiceNull() {
		List<Appointment> appointmentsByService = null;
		String error = null;
		try {
			appointmentsByService = appointmentService.getAppointmentsByService(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(appointmentsByService);
		assertEquals("Invalid service", error);
	}

	/**
	 * Test getVehicle in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetVehicle() {
		Vehicle vehicle = null;

		try {
			vehicle = appointmentService.getVehicle(LICENSEPLATE1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(vehicle);
		assertEquals(LICENSEPLATE1, vehicle.getLicensePlate());
	}

	/**
	 * Test getVehicle when licensePlate does not registered in db.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetVehicleNotExistLicensePlate() {
		Vehicle vehicle = null;
		String error = null;

		try {
			vehicle = appointmentService.getVehicle("NotExistLicense");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(vehicle);
		assertEquals("ERROR: the vehicle cannot be found.", error);
	}

	/**
	 * Test getVehicle when licensePlate passed is null.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetVehicleNullLicensePlate() {
		Vehicle vehicle = null;
		String error = null;

		try {
			vehicle = appointmentService.getVehicle(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(vehicle);
		assertEquals("ERROR: the vehicle licensePlate is null.", error);
	}

	/**
	 * Test getCustomerOfVehicle in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetCustomerOfVehicle() {
		Customer customer = null;

		try {
			customer = appointmentService.getCustomerOfVehicle(VEHICLE);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(customer);
		assertEquals(true, customer.getVehicle().iterator().hasNext());
		assertEquals(VEHICLE, customer.getVehicle().iterator().next());
	}

	/**
	 * Test getCustomerOfVehicle when input vehicle is null.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetCustomerOfVehicleNullVehicle() {
		Customer customer = null;
		String error = null;

		try {
			customer = appointmentService.getCustomerOfVehicle(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(customer);
		assertEquals("ERROR: the vehicle is null.", error);
	}

	/**
	 * Test getCustomerOfVehicle when input vehicle is not registered in database
	 * with any customer.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetCustomerOfVehicleCustomerNotFound() {
		Customer customer = null;
		String error = null;
		Vehicle vehicle = new Vehicle();
		vehicle.setLicensePlate("xxx3");

		try {
			customer = appointmentService.getCustomerOfVehicle(vehicle);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(customer);
		assertEquals("ERROR: the customer cannot be found.", error);
	}

	/**
	 * Test getService in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetService() {
		Service service = null;

		try {
			service = appointmentService.getService(SERVICE1);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(service);
		assertEquals(SERVICE1, service.getServiceName());
	}

	/**
	 * Test getService when input a null service name.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetServiceNullServiceName() {
		Service service = null;
		String error = null;

		try {
			service = appointmentService.getService(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(service);
		assertEquals("ERROR: the service name is null.", error);
	}

	/**
	 * Test getService when in db can't find such a service
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetServiceServiceNotExist() {
		Service service = null;
		String error = null;

		try {
			service = appointmentService.getService("TomCat");
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(service);
		assertEquals("ERROR: the service cannot be found.", error);
	}

	/**
	 * Test createInvoice in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestcreateInvoice() {
		Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
		Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(APPOINTMENT_ID1);
		appointment.setService(service1);

		Invoice invoice = null;

		try {
			invoice = appointmentService.createInvoice(appointment);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(invoice);
		assertEquals(service1.getPrice(), invoice.getCost());
		assertEquals(false, invoice.getIsPaid());
		assertEquals(String.valueOf(service1.getPrice() * appointment.getAppointmentID().hashCode()),
				invoice.getInvoiceID());
	}

	/**
	 * Test getAppointmentsByID in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetAppointmentsByID() {
		Appointment appointment = null;

		try {
			appointment = appointmentService.getAppointmentsByID(APPOINTMENT_ID1);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		assertNotNull(appointment);
		assertEquals(APPOINTMENT_ID1, appointment.getAppointmentID());
	}

	/**
	 * Test getAppointmentsByID in normal case.
	 * 
	 * @author Zichen
	 */
	@Test
	public void TestgetAppointmentsByIDNullID() {
		Appointment appointment = null;
		String error = null;

		try {
			appointment = appointmentService.getAppointmentsByID(null);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertNotNull(error);
		assertNull(appointment);
		assertEquals("Invalid appointment id", error);
	}

	/*
	 * ------------------------------ Helpers -------------------------------------
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

	private Resource createResource(String type, Integer max) {
		Resource resource = new Resource();
		resource.setResourceType(RESOURCE_TYPE1);
		resource.setMaxAvailable(MAX1);
		return resource;
	}

	private Service createService(String name, Resource resource, double price, Integer f, Integer d) {
		Service service = new Service();
		service.setServiceName(name);
		service.setResource(resource);
		service.setPrice(price);
		service.setFrequency(f);
		service.setDuration(d);

		return service;
	}

	private Vehicle createVehicle(String LicensePlate, String brand, String model, String year) {
		Vehicle vehicle = new Vehicle();
		vehicle.setLicensePlate(LicensePlate);
		vehicle.setBrand(brand);
		vehicle.setModel(model);
		vehicle.setYear(year);

		return vehicle;
	}

	private Invoice createInvoice(String id, double cost, boolean ispaid) {
		Invoice invoice = new Invoice();
		invoice.setInvoiceID(id);
		invoice.setCost(cost);
		invoice.setIsPaid(ispaid);

		return invoice;
	}

	private Set<DailyAvailability> createSetAvailabilities() {
		Set<DailyAvailability> dailyAvailabilities = new HashSet<DailyAvailability>();
		Integer i = 1;
		for (DayOfWeek day : DayOfWeek.values()) {
			DailyAvailability dailyAvailability = new DailyAvailability();
			dailyAvailability.setDay(day);
			dailyAvailability.setStartTime(Time.valueOf(LocalTime.of(9, 00)));
			dailyAvailability.setEndTime(Time.valueOf(LocalTime.of(17, 00)));
			dailyAvailability.setAvailabilityID((i++).toString());
			dailyAvailabilities.add(dailyAvailability);
		}

		return dailyAvailabilities;
	}

	private Timeslot createTimeslot(Date date, Time time, String id) {
		Timeslot slot = new Timeslot();
		slot.setDate(date);
		slot.setTime(time);
		slot.setSlotID(id);

		return slot;
	}

	private Technician mockTechnician(Set<DailyAvailability> dailyAvailabilities, Set<Service> services) {
		Technician tech = new Technician();
		tech.setProfileID(TECH_ID);
		tech.setEmail(TECH_EMAIL);
		tech.setFirstName(TECH_FIRSTNAME);
		tech.setLastName(TECH_LASTNAME);
		tech.setIsRegisteredAccount(ISREGISTERED);
		tech.setPassword(TECH_PASSWORD);
		tech.setDailyAvailability(dailyAvailabilities);
		tech.setService(services);

		return tech;
	}

	private Customer mockCustomer(Set<Vehicle> vehicles) {
		Customer customer = new Customer();
		customer.setEmail(CUST_EMAIL);
		customer.setFirstName(CUST_FIRSTNAME);
		customer.setLastName(CUST_LASTNAME);
		customer.setIsRegisteredAccount(ISREGISTERED);
		customer.setPassword(CUST_PASSWORD);
		customer.setPhoneNumber(CUST_PHONE);
		customer.setProfileID(CUST_ID);
		customer.setVehicle(vehicles);

		return customer;
	}

}
