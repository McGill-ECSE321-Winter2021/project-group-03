package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Set;

/**
 * Data transfer object class for customer profile.
 * 
 * @author Jack Wei
 *
 */
public class CustomerDto {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	//private String password;
	private Set<VehicleDto> vehicles;

	public CustomerDto() {
	}

	public CustomerDto(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public CustomerDto(String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
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

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}

	public Set<VehicleDto> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<VehicleDto> vehicles) {
		this.vehicles = vehicles;
	}

}
