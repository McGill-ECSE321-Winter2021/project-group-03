package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.Timeslot;

public interface TimeslotRepository extends CrudRepository<Timeslot, String> {

	Timeslot findTimeslotBySlotID(String slotID);

}
