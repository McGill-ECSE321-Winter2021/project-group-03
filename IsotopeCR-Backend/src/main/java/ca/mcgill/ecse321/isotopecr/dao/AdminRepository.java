package ca.mcgill.ecse321.isotopecr.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.Admin;


public interface AdminRepository extends CrudRepository <Admin,String>{

	Admin findAdminByProfileID(String profileID);
	
}
