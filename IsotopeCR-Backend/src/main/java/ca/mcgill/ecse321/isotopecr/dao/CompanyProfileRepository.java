package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.CompanyProfile;

public interface CompanyProfileRepository extends CrudRepository<CompanyProfile, String> {

	CompanyProfile findCompanyProfileByAddress(String address);

}
