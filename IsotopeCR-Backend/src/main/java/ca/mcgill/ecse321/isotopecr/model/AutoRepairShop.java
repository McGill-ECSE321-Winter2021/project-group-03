package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class AutoRepairShop {
	private List<Profile> profiles;
	
	@OneToMany(cascade={CascadeType.ALL})
	public List<Profile> getProfiles(){
		return this.profiles;
	}
	
	public void setProfiles(List<Profile> profiles) {
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
	
	
	private List<Appointment> appointments;
	
	@OneToMany(cascade={CascadeType.ALL})
	public List<Appointment> getAppointments(){
		return this.appointments;
	}
	
	public void setAppointments(List<Appointment> appointments) {
		   this.appointments = appointments;
	}
	
	
	private List<Timeslot> timeSlots;
	
	@OneToMany(cascade={CascadeType.ALL})
	public List<Timeslot> getTimeSlots(){
		return this.timeSlots;
	}
	
	public void setTimeSlots(List<Timeslot> timeSlots) {
		   this.timeSlots = timeSlots;
	}
	
	private List<Resource> resources;
	
	@OneToMany(cascade={CascadeType.ALL})
	public List<Resource> getResources(){
		return this.resources;
	}
	
	public void setResources(List<Resource> resources) {
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
