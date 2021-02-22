package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profile{
   private String profileID;

public void setProfileID(String value) {
    this.profileID = value;
}
@Id
public String getProfileID() {
    return this.profileID;
}
private String firstName;

public void setFirstName(String value) {
    this.firstName = value;
}
public String getFirstName() {
    return this.firstName;
}
private String lastName;

public void setLastName(String value) {
    this.lastName = value;
}
public String getLastName() {
    return this.lastName;
}
private String email;

public void setEmail(String value) {
    this.email = value;
}
public String getEmail() {
    return this.email;
}
private String password;

public void setPassword(String value) {
    this.password = value;
}
public String getPassword() {
    return this.password;
}
private Boolean isRegisteredAccount;

public void setIsRegisteredAccount(Boolean value) {
    this.isRegisteredAccount = value;
}
public Boolean getIsRegisteredAccount() {
    return this.isRegisteredAccount;
}
}
