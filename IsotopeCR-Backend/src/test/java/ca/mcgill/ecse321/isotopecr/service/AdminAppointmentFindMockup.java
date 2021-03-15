package ca.mcgill.ecse321.isotopecr.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.HashSet;
import java.util.Set;

import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.isotopecr.model.Admin;
import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.Invoice;
import ca.mcgill.ecse321.isotopecr.model.Resource;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;

public class AdminAppointmentFindMockup {
	 /* -------------------------- AdminRepo ------------------------- */
    /** 
     * Mock behavior for findAdminByProfileID 
     */
    lenient().when(adminRepository.findAdminByProfileID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(ADMIN_ID)) {
            Admin admin = new Admin();
            admin.setProfileID(ADMIN_ID);
            admin.setEmail(ADMIN_EMAIL);
            admin.setFirstName(ADMIN_ID);
            admin.setLastName(ADMIN_ID);
            admin.setIsOwner(ISOWNER);
            admin.setIsRegisteredAccount(ISREGISTERED);        
            admin.setPassword(ADMIN_ID);	            
            return admin;
        } else {
            return null;
        }
    });
    
    
    /* ---------------------------- AppointmentRepo -------------------------- */
    /** 
     * Mock behavior for findAdminByProfileID 
     */
    lenient().when(appointmentRepository.findAppointmentByAppointmentID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(ADMIN_ID)) {
            Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
            Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);
           	        	
        	Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
            Service service2 = createService(SERVICE2, resource2, PRICE1, FREQUENCY1, DURATION1);	         
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
        
            appointment.setAppointmentID(APPOINTMENT_ID);
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
    
    
    lenient().when(appointmentRepository.findAppointmentByCustomer(any(Customer.class))).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(CUSTOMER)) {
        	Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
            Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);
           	        	
        	Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
            Service service2 = createService(SERVICE2, resource2, PRICE1, FREQUENCY1, DURATION1);	         
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
        
            appointment.setAppointmentID(APPOINTMENT_ID);
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
    
    
    lenient().when(appointmentRepository.findAppointmentByVehicle(any(Vehicle.class))).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(VEHICLE)) {
        	Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
            Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);
           	        	
        	Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
            Service service2 = createService(SERVICE2, resource2, PRICE1, FREQUENCY1, DURATION1);	         
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
        
            appointment.setAppointmentID(APPOINTMENT_ID);
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
    
    
    lenient().when(appointmentRepository.findAppointmentByTechnician(any(Technician.class))).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(TECHNICIAN)) {
        	Resource resource1 = createResource(RESOURCE_TYPE1, MAX1);
            Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);
           	        	
        	Service service1 = createService(SERVICE1, resource1, PRICE1, FREQUENCY1, DURATION1);
            Service service2 = createService(SERVICE2, resource2, PRICE1, FREQUENCY1, DURATION1);	         
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
        
            appointment.setAppointmentID(APPOINTMENT_ID);
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
    
 // TODO: not found inside Service!!!! =============================
    //==============================================================
//    lenient().when(appointmentRepository.findAppointmentByTimeslot(any(Technician.class))).thenAnswer( (InvocationOnMock invocation) -> {
//        if(invocation.getArgument(0).equals(CUSTOMER)) {
//        	
//        } else {
//            return null;
//        }
//    });
    
    lenient().when(appointmentRepository.findAppointmentByService(any(Service.class))).thenAnswer( (InvocationOnMock invocation) -> {
        if(invocation.getArgument(0).equals(SERVICE)) {

            Resource resource2 = createResource(RESOURCE_TYPE2, MAX2);
           	        	
        	Service service1 = SERVICE;
            Service service2 = createService(SERVICE2, resource2, PRICE1, FREQUENCY1, DURATION1);	         
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
        
            appointment.setAppointmentID(APPOINTMENT_ID);
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
}
