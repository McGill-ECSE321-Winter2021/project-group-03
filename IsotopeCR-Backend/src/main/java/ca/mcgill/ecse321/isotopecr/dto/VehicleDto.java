package ca.mcgill.ecse321.isotopecr.dto;

public class VehicleDto {
	private String licensePlate;
	private Integer year;
	private String model;
	private String brand;
	private CustomerDto owner;
	
	public VehicleDto() {
		
	}
	
	public VehicleDto(String licensePlate, int year, String model, String brand) {
		this.licensePlate = licensePlate;
		this.year = year;
		this.model = model;
		this.brand = brand;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public CustomerDto getOwner() {
		return owner;
	}
}
