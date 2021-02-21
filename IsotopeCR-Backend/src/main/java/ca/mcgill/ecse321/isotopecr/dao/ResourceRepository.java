package ca.mcgill.ecse321.isotopecr.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.Resource;

public interface ResourceRepository extends CrudRepository <Resource,String> {

	Resource findResourceByResourceType(String resourceType);
	
}
