package ca.mcgill.ecse321.isotopecr.dto;

public class CompanyProfileDto {
	private String companyName;
	private String address;
	private String workingHours;

	public CompanyProfileDto() {

	}

	public CompanyProfileDto(String companyName, String address, String workingHours) {
		this.companyName = companyName;
		this.address = address;
		this.workingHours = workingHours;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public String getAddress() {
		return this.address;
	}

	public String getWorkingHours() {
		return this.workingHours;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

}
