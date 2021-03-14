package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Set;

/**
 * Data transfer object class for admin profile.
 * 
 * @author Jack Wei
 *
 */
public class AdminDto {
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Boolean isOwner;

	public AdminDto() {
	}
	
	public AdminDto(String firstName, String lastName, String email, Boolean isOwner) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isOwner = isOwner;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
	}

}
