package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Set;
/**
 * Data transfer object class for customer profile.
 * 
 * @author Jack Wei
 *
 */
public class CustomerDto {
	private String profileID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String password;
	private Set <VehicleDto> vehicles;
	private Boolean isRegisteredAccount;
	
	public CustomerDto() {
	}
	
	public CustomerDto(String profileID, String firstName, String lastName, String email, String phoneNumber, String password, Set <VehicleDto> vehicles, Boolean isRegisteredAccount) {
		this.profileID = profileID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.vehicles = vehicles;
		this.isRegisteredAccount = isRegisteredAccount;
	}

	public String getProfileID() {
		return profileID;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getPassword() {
		return password;
	}

	public Set<VehicleDto> getVehicles() {
		return vehicles;
	}
	
	public Boolean getIsRegisteredAccount() {
		return isRegisteredAccount;
	}

}
