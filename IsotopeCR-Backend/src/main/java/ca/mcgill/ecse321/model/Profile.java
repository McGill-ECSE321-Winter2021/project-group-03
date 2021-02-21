package ca.mcgill.ecse321.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ProfileType")
public abstract class Profile {
	
	private String profileID;
	
	@Id
	public String getProfileID() {
		return this.profileID;
	}
	
	public void setProfileID(String id) {
		this.profileID = id;
	}
	
	private String firstName;
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	private String lastName;
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	private String email;
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	private String password;
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	private boolean isRegisteredAccount;
	
	public boolean getIsRegisteredAccount() {
		return this.isRegisteredAccount;
	}
	
	public void setIsRegisteredAccount(boolean isRegisteredAccount) {
		this.isRegisteredAccount = isRegisteredAccount;
	}

}

