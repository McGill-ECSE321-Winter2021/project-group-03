package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Data transfer object class for technician profile.
 * 
 * @author Jack Wei
 *
 */
public class TechnicianDto {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Set<ServiceDto> services;
	private List<DailyAvailabilityDto> dailyAvailabilities;

	public TechnicianDto() {
	}

	public TechnicianDto(String firstName, String lastName, String email, String password, List<DailyAvailabilityDto> dailyAvailabilities) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.services = Collections.EMPTY_SET;
		this.dailyAvailabilities = dailyAvailabilities;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<ServiceDto> getServices() {
		return services;
	}

	public void setServices(Set<ServiceDto> services) {
		this.services = services;
	}

	public String getPassword() {
		return password;
	}

	public List<DailyAvailabilityDto> getDailyAvailabilities() {
		return dailyAvailabilities;
	}

	public void setDailyAvailabilities(List<DailyAvailabilityDto> dailyAvailabilities) {
		this.dailyAvailabilities = dailyAvailabilities;
	}

}
