package ca.mcgill.ecse321.isotopecr.dao;

import java.util.*;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.*;

public interface ServiceRepository extends CrudRepository<Service, String> {

	Service findServiceByServiceName(String serviceName);

	List<Service> findServiceByResource(Resource resource);

}
