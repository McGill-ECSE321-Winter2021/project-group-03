package ca.mcgill.ecse321.isotopecr.model;

import javax.persistence.*;

@Entity
public class Vehicle {

	private String licensePlate;

	public void setLicensePlate(String value) {
		this.licensePlate = value;
	}

	@Id
	public String getLicensePlate() {
		return this.licensePlate;
	}

	private String year;

	public void setYear(String value) {
		this.year = value;
	}

	public String getYear() {
		return this.year;
	}

	private String model;

	public void setModel(String value) {
		this.model = value;
	}

	public String getModel() {
		return this.model;
	}

	private String brand;

	public void setBrand(String value) {
		this.brand = value;
	}

	public String getBrand() {
		return this.brand;
	}
}
