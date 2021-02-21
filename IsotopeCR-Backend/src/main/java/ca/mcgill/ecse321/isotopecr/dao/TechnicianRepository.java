package ca.mcgill.ecse321.isotopecr.dao;
import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.Service;
import ca.mcgill.ecse321.isotopecr.model.Technician;

public interface TechnicianRepository extends CrudRepository <Technician,String>{
	
   Technician findTechnicianByProfileID(String profileID); 
   
   //List<Technician> findByService(Service service);
}
