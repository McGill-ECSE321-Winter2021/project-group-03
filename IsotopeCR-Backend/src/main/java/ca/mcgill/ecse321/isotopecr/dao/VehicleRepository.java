package ca.mcgill.ecse321.isotopecr.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.isotopecr.model.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

	Vehicle findVehicleByLicensePlate(String licensePlate);

}
