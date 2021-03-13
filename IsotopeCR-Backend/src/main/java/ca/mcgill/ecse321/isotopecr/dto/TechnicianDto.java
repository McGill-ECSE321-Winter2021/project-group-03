package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Collections;
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
	private Set <ServiceDto> services;
	private Set <DailyAvailabilityDto> dailyAvailabilities;
	
	public TechnicianDto() {
	}
	
	public TechnicianDto(String firstName, String lastName, String email, String password, Set <ServiceDto> services, Set <DailyAvailabilityDto> dailyAvailabilities) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.services = services;
		this.dailyAvailabilities = dailyAvailabilities;
	}
	
	public TechnicianDto(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.services = Collections.EMPTY_SET;
		this.dailyAvailabilities = Collections.emptySet();
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

	public Set<DailyAvailabilityDto> getDailyAvailabilities() {
		return dailyAvailabilities;
	}
	
	public void setDailyAvailabilities(Set<DailyAvailabilityDto> dailyAvailabilities) {
		this.dailyAvailabilities = dailyAvailabilities;
	}
	
}
