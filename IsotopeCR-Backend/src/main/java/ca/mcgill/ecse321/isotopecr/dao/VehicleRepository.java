package ca.mcgill.ecse321.isotopecr.dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;


public interface VehicleRepository extends CrudRepository<Vehicle,String> {

	Vehicle findVehicleByLicensePlate(String licensePlate);
	
//	List<Vehicle> findByCustomer(Customer customer);
}
