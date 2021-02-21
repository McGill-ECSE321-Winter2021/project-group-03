package ca.mcgill.ecse321.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompanyProfile {
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	private String address;
	@Id
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	private String workingHours;
	
	public String getWorkingHours() {
		return this.workingHours;
	}
	
	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}
}
