package ca.mcgill.ecse321.isotopecr.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.isotopecr.model.AutoRepairShop;

public interface AutoRepairShopRepository extends CrudRepository<AutoRepairShop,String>{
	
    AutoRepairShop findAutoRepairShopByAutoRepairShopID(String autoRepairShopID);
  
}

