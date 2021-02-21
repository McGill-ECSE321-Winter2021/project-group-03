package ca.mcgill.ecse321.isotopecr.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.model.Customer;
import ca.mcgill.ecse321.model.Vehicle;


public interface CustomerRepository extends CrudRepository <Customer,String> {

	Customer findCustomerByProfileID(String profileID);
	
	Customer findByVehicle(Vehicle vehicle);
}
