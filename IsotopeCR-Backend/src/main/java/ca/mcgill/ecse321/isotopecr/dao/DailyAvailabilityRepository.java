package ca.mcgill.ecse321.isotopecr.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;
import ca.mcgill.ecse321.isotopecr.model.Technician;

public interface DailyAvailabilityRepository extends CrudRepository<DailyAvailability,String>  {
 
	DailyAvailability findDailyAvailabilityByAvailabilityID(String availabilityID);
	
	//List<DailyAvailability> findByTechnician (Technician technician);
}
