package ca.mcgill.ecse321.isotopecr.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.Customer;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;


public interface CustomerRepository extends CrudRepository <Customer,String> {

	Customer findCustomerByProfileID(String id);
	
	Customer findByVehicles(Vehicle vehicles);
}
