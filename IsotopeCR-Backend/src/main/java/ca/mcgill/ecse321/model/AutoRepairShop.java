package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	
	
	private CompanyProfile companyProfile;
	
	@OneToOne
	public CompanyProfile getCompanyProfile() {
		return this.companyProfile;
	}
	
	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}
	
	
	private Set<Appointment> appointments;
	
	@OneToMany(cascade={CascadeType.ALL})
	public Set<Appointment> getAppointments(){
		return this.appointments;
	}
	
	public void setAppointments(Set<Appointment> appointments) {
		   this.appointments = appointments;
	}
	
	
	private Set<Timeslot> timeSlots;
	
	@OneToMany(cascade={CascadeType.ALL})
	public Set<Timeslot> getTimeSlots(){
		return this.timeSlots;
	}
	
	public void setTimeSlots(Set<Timeslot> timeSlots) {
		   this.timeSlots = timeSlots;
	}
	
	private Set<Resource> resources;
	
	@OneToMany(cascade={CascadeType.ALL})
	public Set<Resource> getResources(){
		return this.resources;
	}
	
	public void setResources(Set<Resource> resources) {
		   this.resources = resources;
	}
	
	
	private Set<Service> services;
	
	@OneToMany(cascade={CascadeType.ALL})
	public Set<Service> getServices(){
		return this.services;
	}
	
	public void setServices(Set<Service> services) {
		   this.services = services;
	}

	
}
