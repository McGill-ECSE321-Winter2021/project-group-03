package ca.mcgill.ecse321.isotopecr.dto;

public class ProfileDto {
	private String email;
	private String firstName;
	private String lastName;
	
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
}
