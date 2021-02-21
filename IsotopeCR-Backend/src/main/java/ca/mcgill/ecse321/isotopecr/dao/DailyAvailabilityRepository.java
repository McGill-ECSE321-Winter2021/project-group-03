package ca.mcgill.ecse321.isotopecr.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.model.DailyAvailability;
import ca.mcgill.ecse321.model.Technician;

public interface DailyAvailabilityRepository extends CrudRepository<DailyAvailability,String>  {
 
	DailyAvailability findDailyAvailabilityByAvailabilityID(String availabilityID);
	
	List <DailyAvailability> findByTechnician (Technician technician);
}
