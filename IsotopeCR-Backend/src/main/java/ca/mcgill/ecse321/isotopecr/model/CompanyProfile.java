package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;

@Entity
public class CompanyProfile {
	private String companyName;

	public void setCompanyName(String value) {
		this.companyName = value;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	private String address;

	public void setAddress(String value) {
		this.address = value;
	}

	@Id
	public String getAddress() {
		return this.address;
	}

	private String workingHours;

	public void setWorkingHours(String value) {
		this.workingHours = value;
	}

	public String getWorkingHours() {
		return this.workingHours;
	}
}
