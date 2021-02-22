package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.DailyAvailability;

public interface DailyAvailabilityRepository extends CrudRepository<DailyAvailability, String> {

	DailyAvailability findDailyAvailabilityByAvailabilityID(String availabilityID);

}
