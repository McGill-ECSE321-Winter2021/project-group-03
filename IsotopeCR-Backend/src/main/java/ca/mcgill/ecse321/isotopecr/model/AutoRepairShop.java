package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.List;
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
	
	
	private List<Service> services;
	
	@OneToMany(cascade={CascadeType.ALL})
	public List<Service> getServices(){
		return this.services;
	}
	
	public void setServices(List<Service> services) {
		   this.services = services;
	}

    private String ID;

    @Id
	public String getID() {
		return this.ID;
	}
	
	public void setID(String slotID) {
		this.ID = slotID;
	}

}
