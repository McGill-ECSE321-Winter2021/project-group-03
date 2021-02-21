package ca.mcgill.ecse321.isotopecr.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.model.Customer;
import ca.mcgill.ecse321.model.Vehicle;


public interface VehicleRepository extends CrudRepository<Vehicle,String> {

	Vehicle findVehicleByLicensePlate(String licensePlate);
	
	List<Vehicle> findByCustomer(Customer customer);
}
