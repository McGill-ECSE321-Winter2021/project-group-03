package ca.mcgill.ecse321.isotopecr.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.model.Resource;
import ca.mcgill.ecse321.model.Service;


public interface ServiceRepository extends CrudRepository <Service,String> {

	Service findServiceByName(String name);
	
	List<Service> findByResource(Resource resource);
	
	//boolean existsByName(String name);
}
