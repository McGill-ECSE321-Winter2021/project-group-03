package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.Technician;

public interface TechnicianRepository extends CrudRepository<Technician, String> {

	Technician findTechnicianByProfileID(String profileID);
	
	Technician findTechnicianByEmail(String email);

}
