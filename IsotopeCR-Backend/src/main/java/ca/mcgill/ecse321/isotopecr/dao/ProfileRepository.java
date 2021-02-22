package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, String> {

	Profile findProfileByProfileID(String profileID);

}
