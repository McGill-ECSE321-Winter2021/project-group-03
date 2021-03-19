package ca.mcgill.ecse321.isotopecr.dto;

public class VehicleDto {
	private String licensePlate;
	private String year;
	private String model;
	private String brand;
	
	public VehicleDto() {

	}

	public VehicleDto(String licensePlate, String year, String model, String brand) {
		this.licensePlate = licensePlate;
		this.year = year;
		this.model = model;
		this.brand = brand;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public String getYear() {
		return year;
	}

	public String getModel() {
		return model;
	}

	public String getBrand() {
		return brand;
	}
}
