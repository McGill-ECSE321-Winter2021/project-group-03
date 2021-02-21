package ca.mcgill.ecse321.isotopecr.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.model.Appointment;
import ca.mcgill.ecse321.model.Customer;
import ca.mcgill.ecse321.model.Service;
import ca.mcgill.ecse321.model.Technician;
import ca.mcgill.ecse321.model.Timeslot;
import ca.mcgill.ecse321.model.Vehicle;


public interface AppointmentRepository extends CrudRepository<Appointment,String> {

	Appointment findAppointmentByAppointmentID(String appointmentID);
	
	List<Appointment> findByCustomer(Customer customer);
	List<Appointment> findByVehicle(Vehicle vehicle);
	List<Appointment> findByTechnician(Technician technician);
	List<Appointment> findByTimeslot(Timeslot timeslot);
	List<Appointment> findByService(Service service);
	
	Appointment findByVehicleAndTimeslot(Vehicle vehicle,Timeslot timeslot);
}
