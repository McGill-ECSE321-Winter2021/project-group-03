package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Set;
/**
 * Data transfer object class for technician profile.
 * 
 * @author Jack Wei
 *
 */
public class TechnicianDto {
	
	private String profileID;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Set <ServiceDto> services;
	private Set <DailyAvailabilityDto> dailyAvailabilities;
	
	public TechnicianDto() {
	}
	
	public TechnicianDto(String profileID, String firstName, String lastName, String email, String password, Set <ServiceDto> services, Set <DailyAvailabilityDto> dailyAvailabilities) {
		this.profileID = profileID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.services = services;
		this.dailyAvailabilities = dailyAvailabilities;
	}
	
	
	public String getProfileID() {
		return profileID;
	}
	
	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<DailyAvailabilityDto> getDailyAvailabilities() {
		return dailyAvailabilities;
	}
	
	public void setDailyAvailabilities(Set<DailyAvailabilityDto> availabilities) {
		this.dailyAvailabilities = availabilities;
	}

}
