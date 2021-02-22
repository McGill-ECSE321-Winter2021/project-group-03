package ca.mcgill.ecse321.isotopecr.dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.Appointment;
import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;

public interface AppointmentRepository extends CrudRepository<Appointment,String> {

	Appointment findAppointmentByAppointmentID(String appointmentID);
	
	List<Appointment> findByCustomer(Customer customer);
	List<Appointment> findByVehicle(Vehicle vehicle);
	List<Appointment> findByTechnician(Technician technician);
	List<Appointment> findByTimeslot(Timeslot timeslot);
	List<Appointment> findByService(Service service);
	
//	Appointment findByVehicleAndTimeslot(Vehicle vehicle,Timeslot timeslot);
}
