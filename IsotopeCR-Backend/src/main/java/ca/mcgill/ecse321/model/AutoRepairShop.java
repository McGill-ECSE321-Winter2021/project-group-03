package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class AutoRepairShop {
	private Set<Profile> profiles;
	
	@OneToMany(cascade={CascadeType.ALL})
	public Set<Profile> getProfiles(){
		return this.profiles;
	}
	
	public void setProfiles(Set<Profile> profiles) {
		   this.profiles = profiles;
	}
	
	private Set<Registration> registrations;
}
