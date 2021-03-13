package ca.mcgill.ecse321.isotopecr.dto;

import java.util.Set;
/**
 * Data transfer object class for admin profile.
 * 
 * @author Jack Wei
 *
 */
public class AdminDto {
	private String profileID;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Boolean isOwner;
	
	public AdminDto() {
	}
	
	public AdminDto(String profileID, String firstName, String lastName, String email, String password, Boolean isOwner) {
		this.profileID = profileID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isOwner = isOwner;
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
