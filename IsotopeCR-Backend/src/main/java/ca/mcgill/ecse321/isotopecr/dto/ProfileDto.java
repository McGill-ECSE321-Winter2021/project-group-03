package ca.mcgill.ecse321.isotopecr.dto;

public class ProfileDto {
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String isOwner;
	private String type;

	public ProfileDto() {
	}

	public ProfileDto(String email, String firstName, String lastName) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
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
	
	public String getIsOwner() {
		return isOwner;
	}
	
	public String getType() {
		return type;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
