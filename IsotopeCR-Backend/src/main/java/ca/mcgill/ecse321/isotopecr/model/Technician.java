package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Technician extends Profile {
	private Set<Service> service;

	@ManyToMany
	public Set<Service> getService() {
		return this.service;
	}

	public void setService(Set<Service> services) {
		this.service = services;
	}

	private Set<DailyAvailability> dailyAvailability;

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<DailyAvailability> getDailyAvailability() {
		return this.dailyAvailability;
	}

	public void setDailyAvailability(Set<DailyAvailability> dailyAvailabilitys) {
		this.dailyAvailability = dailyAvailabilitys;
	}

}
