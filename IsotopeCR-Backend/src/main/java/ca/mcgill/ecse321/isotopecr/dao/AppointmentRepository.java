package ca.mcgill.ecse321.isotopecr.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.*;

public interface AppointmentRepository extends CrudRepository<Appointment, String> {

	Appointment findAppointmentByAppointmentID(String appointmentID);

	List<Appointment> findAppointmentByCustomer(Customer customer);

	List<Appointment> findAppointmentByVehicle(Vehicle vehicle);

	List<Appointment> findAppointmentByTechnician(Technician technician);

	List<Appointment> findAppointmentByTimeslot(Timeslot timeslot);

	List<Appointment> findAppointmentByService(Service service);

	List<Appointment> findAppointmentByCustomerAndService(Customer customer, Service service);
}
