package ca.mcgill.ecse321.isotopecr.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.model.Resource;

public interface ResourceRepository extends CrudRepository <Resource,String> {

	Resource findResouceByResouceType(String resouceType);
	
}
