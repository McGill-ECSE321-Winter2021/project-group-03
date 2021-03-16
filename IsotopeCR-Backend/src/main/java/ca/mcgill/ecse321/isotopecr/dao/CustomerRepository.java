package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.*;

public interface CustomerRepository extends CrudRepository<Customer, String> {

	Customer findCustomerByProfileID(String id);

	Customer findCustomerByVehicle(Vehicle vehicle);
	
	Customer findCustomerByEmail(String email);

}
